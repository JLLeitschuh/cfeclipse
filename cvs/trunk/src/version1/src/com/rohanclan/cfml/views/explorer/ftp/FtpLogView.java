/*
 * Created on Nov 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer.ftp;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.enterprisedt.net.ftp.FTPMessageListener;
import com.rohanclan.cfml.ftp.FtpConnection;
import com.rohanclan.cfml.ftp.LogListener;

/**
 * @author Stephen Milligan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FtpLogView extends ViewPart implements FTPMessageListener {
    FtpConnection ftpClient = null;
    private StyledText styledText;
    public void createPartControl(Composite parent) {
        ftpClient = FtpConnection.getInstance();
        LogListener.addListener(this);
        
        
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new FillLayout());
        styledText = new StyledText(container, SWT.BORDER|SWT.MULTI|SWT.H_SCROLL|SWT.V_SCROLL);
        styledText.setLayout(new FillLayout());
        styledText.setEditable(false);
        
        String log = ftpClient.getLog();
        if (log == null) {
            log = "Log initialized";
        }
        styledText.setText(log);
        styledText.setTopIndex(styledText.getLineCount());
        
        //
        createActions();
        initializeToolBar();
        initializeMenu();
    }

    private void createActions() {
    }

    private void initializeToolBar() {
        IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
    }

    private void initializeMenu() {
        IMenuManager manager = getViewSite().getActionBars().getMenuManager();
    }

    public void setFocus() {
    }
    /**
     * Log an FTP command being sent to the server
     * 
     * @param cmd   command string
     */
    public void logCommand(String cmd) {
         styledText.append(styledText.getLineDelimiter()+cmd);
         styledText.setTopIndex(styledText.getLineCount());
    }
    
    /**
     * Log an FTP reply being sent back to the client
     * 
     * @param reply   reply string
     */
    public void logReply(String reply) { 
        styledText.append(styledText.getLineDelimiter()+reply);
        styledText.setTopIndex(styledText.getLineCount());
    }

    public void dispose() {
        LogListener.removeListener(this);
    }
}
