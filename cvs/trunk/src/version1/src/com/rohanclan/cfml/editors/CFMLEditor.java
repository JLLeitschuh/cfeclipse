/*
 * Created on Feb 2, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package com.rohanclan.cfml.editors;

//import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IChangeRulerColumn;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.ITextEditorHelpContextIds;
//import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.texteditor.TextOperationAction;
import com.rohanclan.cfml.CFMLPlugin;
import com.rohanclan.cfml.editors.actions.GenericEncloserAction;
import com.rohanclan.cfml.editors.actions.GotoFileAction;
import com.rohanclan.cfml.editors.actions.JumpToDocPos;
import com.rohanclan.cfml.editors.actions.RTrimAction;
import com.rohanclan.cfml.editors.codefolding.CodeFoldingSetter;
import com.rohanclan.cfml.editors.decoration.DecorationSupport;
import com.rohanclan.cfml.editors.dnd.CFEDragDropListener;
import com.rohanclan.cfml.editors.dnd.SelectionCursorListener;
import com.rohanclan.cfml.editors.pairs.CFMLPairMatcher;
import com.rohanclan.cfml.editors.pairs.Pair;
import com.rohanclan.cfml.parser.docitems.CfmlTagItem;
import com.rohanclan.cfml.preferences.CFMLPreferenceManager;
import com.rohanclan.cfml.preferences.EditorPreferenceConstants;
import com.rohanclan.cfml.util.CFPluginImages;
import com.rohanclan.cfml.views.contentoutline.CFContentOutlineView;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.dialogs.MessageDialog;
import java.util.Iterator;
import org.eclipse.ui.texteditor.AnnotationPreference;
import com.rohanclan.cfml.editors.partitioner.CFEPartitioner;
import com.rohanclan.cfml.editors.partitioner.CFEPartition;

/**
 * @author Rob
 * 
 * This is the start of the Editor. It loads up the configuration and starts up
 * the image manager and syntax dictionaries.
 */
public class CFMLEditor extends AbstractDecoratedTextEditor implements
		IPropertyChangeListener {

	protected ColorManager colorManager;

	protected CFConfiguration configuration;

	protected CFMLPairMatcher cfmlBracketMatcher;

	protected GenericEncloserAction testAction;

	final GotoFileAction gfa = new GotoFileAction();

	private final JumpToDocPos jumpAction = new JumpToDocPos();

	private CodeFoldingSetter foldingSetter;

	protected LineNumberRulerColumn fLineNumberRulerColumn;

	/**
	 * The change ruler column.
	 */
	private IChangeRulerColumn fChangeRulerColumn;

	boolean fIsChangeInformationShown;

	private ProjectionSupport fProjectionSupport;

	private DragSource dragsource;

	private SourceViewer viewer;

	/**
	 * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void doSave(IProgressMonitor monitor) {
		//On save parsing should apparently go into a builder.
		if (getPreferenceStore().getBoolean(
				EditorPreferenceConstants.P_RTRIM_ON_SAVE)) {

			((CFEUndoManager) configuration.getUndoManager(this.getSourceViewer()))
					.listenToTextChanges(false);
			RTrimAction trimAction = new RTrimAction();
			trimAction.setActiveEditor(null, getSite().getPage().getActiveEditor());
			trimAction.run(null);
			((CFEUndoManager) configuration.getUndoManager(this.getSourceViewer()))
					.listenToTextChanges(true);
		}
		try {

			super.doSave(monitor);
		} catch (Exception e) {
			e.printStackTrace();
		}

		foldingSetter.docChanged(false);

	}

	public CFMLEditor() {
		super();

		//	this is for bracket matching
		//create the pairs for testing
		Pair parenthesis = new Pair("(", ")", 1);
		Pair curlyBraces = new Pair("{", "}", 1);
		Pair squareBraces = new Pair("[", "]", 1);

		//create the collection
		LinkedList brackets = new LinkedList();
		brackets.add(parenthesis);
		brackets.add(curlyBraces);
		brackets.add(squareBraces);

		//create the CFMLPairMatcher
		cfmlBracketMatcher = new CFMLPairMatcher(brackets);
		//end bracket matching stuff

		colorManager = new ColorManager();
		//setup color coding and the damage repair stuff

		configuration = new CFConfiguration(colorManager, this);
		setSourceViewerConfiguration(configuration);
		//assign the cfml document provider which does the partitioning
		//and connects it to this Edtior

		setDocumentProvider(new CFDocumentProvider());

		// The following is to enable us to listen to changes. Mainly it's used
		// for
		// getting the document filename when a new document is opened.
		IResourceChangeListener listener = new MyResourceChangeReporter();
		CFMLPlugin.getWorkspace().addResourceChangeListener(listener);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		// This ensures that we are notified when the preferences are saved
		CFMLPlugin.getDefault().getPreferenceStore()
				.addPropertyChangeListener(this);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * This method configures the editor but does not define a
	 * <code>SourceViewerConfiguration</code>. When only interested in
	 * providing a custom source viewer configuration, subclasses may extend this
	 * method.
	 */
	protected void initializeEditor() {
		setEditorContextMenuId("#CFMLEditorContext"); //$NON-NLS-1$
		setRulerContextMenuId("#TextRulerContext"); //$NON-NLS-1$
		setHelpContextId(ITextEditorHelpContextIds.TEXT_EDITOR);
		setPreferenceStore(CFMLPlugin.getDefault().getPreferenceStore());
		configureInsertMode(SMART_INSERT, false);
		setInsertMode(INSERT);

	}

	public void createPartControl(Composite parent) {

		//this.parent = parent;
		super.createPartControl(parent);
		this.setBackgroundColor();

		fSourceViewerDecorationSupport.install(getPreferenceStore());

		ProjectionViewer projectionViewer = (ProjectionViewer) getSourceViewer();

		fProjectionSupport = new ProjectionSupport(projectionViewer,
				getAnnotationAccess(), getSharedColors());
		fProjectionSupport
				.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.error");
		fProjectionSupport
				.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.task");
		fProjectionSupport
				.addSummarizableAnnotationType("com.rohanclan.cfml.parserProblemAnnotation");
		fProjectionSupport
				.addSummarizableAnnotationType("com.rohanclan.cfml.parserWarningAnnotation");
		fProjectionSupport
				.addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.warning");
		fProjectionSupport.setHoverControlCreator(new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell shell) {
				return new DefaultInformationControl(shell);
			}
		});
		fProjectionSupport.install();

		projectionViewer.doOperation(ProjectionViewer.TOGGLE);

		foldingSetter = new CodeFoldingSetter(this);
		foldingSetter.docChanged(true);

		// TODO: If we set this directly the projection viewer loses track of the
		// line numbers.
		// Need to create a class that extends projectionViewer so we can implement
		// wrapped
		// line tracking.
		//projectionViewer.getTextWidget().setWordWrap(true);

		createDragAndDrop(projectionViewer);

		try {
			if (isEditorInputReadOnly()
					&& getPreferenceStore().getBoolean(
							EditorPreferenceConstants.P_WARN_READ_ONLY_FILES)) {
				String[] labels = new String[1];
				labels[0] = "OK";
				MessageDialogWithToggle msg = new MessageDialogWithToggle(
						this.getEditorSite().getShell(),
						"Warning!",
						null,
						"You are opening a read only file. You will not be able to make or save any changes.",
						MessageDialog.WARNING, labels, 0,
						"Don't show this warning in future.", false);
				//MessageBox msg = new MessageBox(this.getEditorSite().getShell());
				//msg.setText("Warning!");
				//msg.setMessage("You are opening a read only file. You will not be
				// able to make or save any changes.");
				if (msg.open() == 0) {
					if (msg.getToggleState()) {
						// Don't show warning in future.
						getPreferenceStore().setValue(
								EditorPreferenceConstants.P_WARN_READ_ONLY_FILES, false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public IVerticalRuler verticalRuler() {

		return this.getVerticalRuler();
	}

	private void createDragAndDrop(ProjectionViewer projectionViewer) {

		SelectionCursorListener cursorListener = new SelectionCursorListener(this,
				projectionViewer);
		projectionViewer.getTextWidget().addMouseMoveListener(cursorListener);
		//projectionViewer.getTextWidget().addMouseTrackListener(cursorListener);
		projectionViewer.addSelectionChangedListener(cursorListener);
		projectionViewer.getTextWidget().addMouseListener(cursorListener);
		projectionViewer.getTextWidget().addKeyListener(cursorListener);

		//Allow data to be copied or moved from the drag source
		int operations = DND.DROP_MOVE | DND.DROP_COPY;
		dragsource = new DragSource(this.getSourceViewer().getTextWidget(),
				operations);

		//Allow data to be copied or moved to the drop target
		operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		DropTarget target = new DropTarget(this.getSourceViewer().getTextWidget(),
				operations);

		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		dragsource.setTransfer(types);

		//Receive data in Text or File format
		final TextTransfer textTransfer = TextTransfer.getInstance();
		final FileTransfer fileTransfer = FileTransfer.getInstance();
		final CFMLEditor thistxt = this;

		types = new Transfer[] { fileTransfer, textTransfer };
		target.setTransfer(types);

		CFEDragDropListener ddListener = new CFEDragDropListener(this,
				(ProjectionViewer) this.getSourceViewer(), cursorListener);

		dragsource.addDragListener(ddListener);

		target.addDropListener(ddListener);

	}

	/**
	 * {@inheritDoc}
	 */
	protected void editorContextMenuAboutToShow(IMenuManager menu) {

		addTagSpecificMenuItems(menu);

		super.editorContextMenuAboutToShow(menu);

		//addAction(menu, ITextEditorActionConstants.FIND);
		//this is the right way to do lower case stuff, but how do you get it
		//on the menu?
		//addAction(menu,ITextEditorActionConstants.UPPER_CASE);
		//addAction(menu,ITextEditorActionConstants.LOWER_CASE);
	}

	/**
	 * Add menu items based on the tag that was right clicked on... doesnt work as
	 * I have no idea how to find out what tag was just clicked on :) seems like
	 * perhaps the CFDocument could know...
	 * 
	 * @param menu
	 */
	protected void addTagSpecificMenuItems(IMenuManager menu) {
		//all this mess is really just to get the offset and a handle to the
		//CFDocument object attached to the Document...
		try {
			IEditorPart iep = getSite().getPage().getActiveEditor();
			ITextEditor editor = (ITextEditor) iep;
			IDocument doc = editor.getDocumentProvider().getDocument(
					editor.getEditorInput());

			ICFDocument cfd = (ICFDocument) doc;
			ITextSelection sel = (ITextSelection) editor.getSelectionProvider()
					.getSelection();


			Action act = new Action("Refresh syntax highlighting", null) {
				public void run() {
				    
				  IEditorPart iep = getSite().getPage().getActiveEditor();
					ITextEditor editor = (ITextEditor) iep;
					ISelection sel = editor.getSelectionProvider().getSelection();
					IDocument doc = editor.getDocumentProvider().getDocument(
							editor.getEditorInput());
					String docText = doc.get();
					doc.set("");
					doc.set(docText);
					editor.getSelectionProvider().setSelection(sel);
				}
			};
			menu.add(act);



			act = new Action("Show partition info", null) {
				public void run() {
				    
				    IEditorPart iep = getSite().getPage().getActiveEditor();
					ITextEditor editor = (ITextEditor) iep;
					IDocument doc = editor.getDocumentProvider().getDocument(
							editor.getEditorInput());

					ICFDocument cfd = (ICFDocument) doc;
					ITextSelection sel = (ITextSelection) editor.getSelectionProvider()
							.getSelection();

					int startpos = sel.getOffset();
					int len = Math.max(sel.getLength(),1);
					CFEPartitioner partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
					CFEPartition[] partitioning = partitioner.getCFEPartitions(startpos,startpos+len);
				    String info = "Partitioning info from offset " + startpos + " to " + Integer.toString(startpos + len) + "\n\n";
					for (int i=0;i<partitioning.length;i++) {
					    info += partitioning[i].getType(); 
					    info += " starting at ";
					    info += partitioning[i].getOffset();
					    info += " ending at "; 
					    info += Integer.toString(partitioning[i].getOffset() + partitioning[i].getLength());
					    if (partitioning[i].getTagName() != null) {
					        info += " (";
					        info += partitioning[i].getTagName();
					        info += ") ";
					    }
					    info += "\n";
					}
				    
				    String[] labels = new String[1];
					labels[0] = "OK";
				    MessageDialog msg = new MessageDialog(
							Display.getCurrent().getActiveShell(),
							"Partition info",
							null,
							info,
							MessageDialog.WARNING, 
							labels, 
							0);
				    msg.open();
				}
			};
			menu.add(act);
			
			/*
			 * Start the logic to see if we are in a cfinclude or a cfmodule. get the tag.
			 */
			int startpos = sel.getOffset();
			int len = Math.max(sel.getLength(),1);
			CFEPartitioner partitioner = (CFEPartitioner)cfd.getDocumentPartitioner();
			CFEPartition[] partitioning = partitioner.getCFEPartitions(startpos,startpos+len);
			
			CfmlTagItem cti = null; 
		    try {
		        cti = cfd.getTagAt(startpos, startpos);
		    }
			catch (Exception e) {
			    // do nothing.
			}

			if (cti != null) {

				if (cti.matchingItem != null) {
					jumpAction.setDocPos(cti.matchingItem.getEndPosition());
					jumpAction.setActiveEditor(null, getSite().getPage()
							.getActiveEditor());
					Action jumpNow = new Action("Jump to end tag", CFPluginImages
							.getImageRegistry().getDescriptor(CFPluginImages.ICON_FORWARD)) {
						public void run() {
							jumpAction.run(null);
						}
					};
					menu.add(jumpNow);
				}

				String n = cti.getName();
				if (n.equalsIgnoreCase("cfinclude") || n.equalsIgnoreCase("cfmodule")) {
					//this is a bit hokey - there has to be a way to load the
					//action in the xml file then just call it here...
					gfa.setActiveEditor(null, getSite().getPage().getActiveEditor());

					Action ack = new Action("Open/Create File", CFPluginImages
							.getImageRegistry().getDescriptor(CFPluginImages.ICON_IMPORT)) {
						public void run() {

							gfa.run(null);
						}
					};
					menu.add(ack);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see IAdaptable#getAdapter(java.lang.Class)
	 * @since 2.0
	 */
	public Object getAdapter(Class required) {

		if (fProjectionSupport != null) {
			Object adapter = fProjectionSupport.getAdapter(getSourceViewer(),
					required);
			if (adapter != null)
				return adapter;
		}

		//if they ask for the outline page send our implementation
		if (required.getName().trim().equals(
				"org.eclipse.ui.views.contentoutline.IContentOutlinePage")) {
			try {
				return new CFContentOutlineView();
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			return super.getAdapter(required);
			//return super.getAdapter(adapter);
		}
		//otherwise just send our supers
		else {
			return super.getAdapter(required);
		}
	}

	public void createActions() {
		super.createActions();

		//this sets up the ctrl+space code insight stuff
		try {
			IAction action = new TextOperationAction(CFMLPlugin.getDefault()
					.getResourceBundle(), "ContentAssistProposal.",
			//"ContentAssistTip.",
					this, ISourceViewer.CONTENTASSIST_PROPOSALS
			//ISourceViewer.CONTENTASSIST_CONTEXT_INFORMATION
			);

			action
					.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS
					//ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION
					);

			setAction("ContentAssistAction", action);

			setActionActivationCode(
					ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS, ' ', -1,
					SWT.CTRL);

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	public void dispose() {
		colorManager.dispose();
		if (this.cfmlBracketMatcher != null) {
			this.cfmlBracketMatcher.dispose();
		}
		CFMLPlugin.getDefault().getPreferenceStore().removePropertyChangeListener(
				this);
		CFMLPlugin.getDefault().getLastActionManager().removeAction(this);
		super.dispose();
	}

	public void propertyChange(PropertyChangeEvent event) {
		handlePreferenceStoreChanged(event);
	}

	protected void handlePreferenceStoreChanged(PropertyChangeEvent event) {
		if (event.getProperty().equals("tabsAsSpaces")
				|| event.getProperty().equals("tabWidth")) {

			ISourceViewer sourceViewer = getSourceViewer();
			if (sourceViewer != null) {
				sourceViewer.getTextWidget().setTabs(
						configuration.getTabWidth(sourceViewer));
			}
		}
		setBackgroundColor();

		super.handlePreferenceStoreChanged(event);
	}

	/**
	 * Shows the line number ruler column.
	 */
	private void showLineNumberRuler() {
		showChangeRuler(false);
		if (fLineNumberRulerColumn == null) {
			IVerticalRuler v = getVerticalRuler();
			if (v instanceof CompositeRuler) {
				CompositeRuler c = (CompositeRuler) v;
				c.addDecorator(1, createLineNumberRulerColumn());
			}
		}
	}

	/**
	 * Hides the line number ruler column.
	 */
	private void hideLineNumberRuler() {
		if (fLineNumberRulerColumn != null) {
			IVerticalRuler v = getVerticalRuler();
			if (v instanceof CompositeRuler) {
				CompositeRuler c = (CompositeRuler) v;
				c.removeDecorator(fLineNumberRulerColumn);
			}
			fLineNumberRulerColumn = null;
		}
		if (fIsChangeInformationShown)
			showChangeRuler(true);
	}

	/**
	 * Sets the display state of the separate change ruler column (not the quick
	 * diff display on the line number ruler column) to <code>show</code>.
	 * 
	 * @param show
	 *          <code>true</code> if the change ruler column should be shown,
	 *          <code>false</code> if it should be hidden
	 */
	private void showChangeRuler(boolean show) {
		IVerticalRuler v = getVerticalRuler();
		if (v instanceof CompositeRuler) {
			CompositeRuler c = (CompositeRuler) v;
			if (show && fChangeRulerColumn == null)
				c.addDecorator(1, createChangeRulerColumn());
			else if (!show && fChangeRulerColumn != null) {
				c.removeDecorator(fChangeRulerColumn);
				fChangeRulerColumn = null;
			}
		}
	}

	/**
	 * Set the background color of the editor window based on the user's
	 * preferences
	 */
	private void setBackgroundColor() {
		// Only try to set the background color when the source viewer is
		// available
		if (this.getSourceViewer() != null
				&& this.getSourceViewer().getTextWidget() != null) {
			CFMLPreferenceManager manager = new CFMLPreferenceManager();
			// Set the background color of the editor
			this.getSourceViewer().getTextWidget().setBackground(
					new org.eclipse.swt.graphics.Color(Display.getCurrent(), manager
							.getColor(EditorPreferenceConstants.P_COLOR_BACKGROUND)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#configureSourceViewerDecorationSupport(org.eclipse.ui.texteditor.SourceViewerDecorationSupport)
	 */
	protected void configureSourceViewerDecorationSupport(
			SourceViewerDecorationSupport support) {

		//register the pair matcher
		support.setCharacterPairMatcher(cfmlBracketMatcher);

		//register the brackets and colors
		support.setMatchingCharacterPainterPreferenceKeys(
				EditorPreferenceConstants.P_BRACKET_MATCHING_ENABLED,
				EditorPreferenceConstants.P_BRACKET_MATCHING_COLOR);

		super.configureSourceViewerDecorationSupport(support);
		Iterator e = getAnnotationPreferences().getAnnotationPreferences()
				.iterator();
		while (e.hasNext()) {
			AnnotationPreference pref = (AnnotationPreference) e.next();
			support.setAnnotationPainterPreferenceKeys(pref.getAnnotationType(), pref
					.getColorPreferenceKey(), pref.getTextPreferenceKey(), pref
					.getOverviewRulerPreferenceKey(), 4);
		}
		support.install(this.getPreferenceStore());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#createSourceViewer(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.jface.text.source.IVerticalRuler, int)
	 */
	protected ISourceViewer createSourceViewer(Composite parent,
			IVerticalRuler ruler, int styles) {

		ProjectionViewer viewer = new ProjectionViewer(parent, ruler,
				getOverviewRuler(), isOverviewRulerVisible(), styles);
		// ensure decoration support has been created and configured.
		getSourceViewerDecorationSupport(viewer);
		return viewer;

	}

	/**
	 * Returns the source viewer decoration support.
	 * 
	 * @param viewer
	 *          the viewer for which to return a decoration support
	 * @return the source viewer decoration support
	 */
	protected SourceViewerDecorationSupport getSourceViewerDecorationSupport(
			ISourceViewer viewer) {

		if (fSourceViewerDecorationSupport == null) {
			fSourceViewerDecorationSupport = new DecorationSupport(viewer,
					getOverviewRuler(), getAnnotationAccess(), getSharedColors());
			configureSourceViewerDecorationSupport(fSourceViewerDecorationSupport);
		}
		return fSourceViewerDecorationSupport;
	}

}