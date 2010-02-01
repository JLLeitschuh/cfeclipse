package org.cfeclipse.cfml.editors.formatters;
/*
 * "The Java Developer's Guide to Eclipse"
 *   by D'Anjou, Fairbrother, Kehn, Kellerman, McCarthy
 * 
 * (C) Copyright International Business Machines Corporation, 2003, 2004. 
 * All Rights Reserved.
 * 
 * Code or samples provided herein are provided without warranty of any kind.
 */
/**
 * SQL Syntax words (upper and lower cases).
 */
public interface ISQLSyntax {
  public static final String[] reservedwords = {"ABSOLUTE",
      "ACQUIRE", "ACTION", "ADD", "ALL", "ALLOCATE", "ALTER",
      "AND", "ANY", "ARE", "AS", "ASC", "ASSERTION", "AT",
      "AUDIT", "AUTHORIZATION", "AVG", "BEGIN", "BETWEEN",
      "BIT_LENGTH", "BOTH", "BUFFERPOOL", "BY", "CALL",
      "CAPTURE", "CASCADED", "CASE", "CAST", "CATALOG", "CCSID",
      "CHAR", "CHAR_LENGTH", "CHARACTER", "CHARACTER_LENGTH",
      "CHECK", "CLOSE", "CLUSTER", "COALESCE", "COLLATE",
      "COLLATION", "COLLECTION", "COLUMN", "COMMENT", "COMMIT",
      "CONCAT", "CONNECT", "CONNECTION", "CONSTRAINT",
      "CONSTRAINTS", "CONTINUE", "CONVERT", "CORRESPONDING",
      "COUNT", "CREATE", "CROSS", "CURRENT", "CURRENT_DATE",
      "CURRENT_SERVER", "CURRENT_TIME", "CURRENT_TIMESTAMP",
      "CURRENT_TIMEZONE", "CURRENT_USER", "CURSOR", "DATABASE",
      "DATE", "DAY", "DAYS", "DBA", "DBSPACE", "DEALLOCATE",
      "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DEFERRABLE",
      "DEFERRED", "DELETE", "DESC", "DESCRIBE", "DESCRIPTOR",
      "DIAGNOSTICS", "DISCONNECT", "DISTINCT", "DOMAIN",
      "DOUBLE", "DROP", "EDITPROC", "ELSE", "END", "END-EXEC",
      "ERASE", "ESCAPE", "EXCEPT", "EXCEPTION", "EXCLUSIVE",
      "EXECUTE", "EXISTS", "EXPLAIN", "EXTERNAL", "EXTRACT",
      "FETCH", "FIELDPROC", "FIRST", "FLOAT", "FOR", "FOREIGN",
      "FOUND", "FROM", "FULL", "FULL", "GET", "GLOBAL", "GO",
      "GOTO", "GRANT", "GRAPHIC", "GROUP", "HAVING", "HOUR",
      "HOURS", "IDENTIFIED", "IDENTITY", "IMMEDIATE", "IN",
      "INDEX", "INDICATOR", "INITIALLY", "INNER", "INNER",
      "INOUT", "INPUT", "INSENSITIVE", "INSERT", "INTERSECT",
      "INTERVAL", "INTO", "IS", "ISOLATION", "JOIN", "JOIN",
      "KEY", "LABEL", "LANGUAGE", "LAST", "LEADING", "LEFT",
      "LEFT", "LEVEL", "LIKE", "LOCAL", "LOCK", "LOCKSIZE",
      "LONG", "LOWER", "MATCH", "MAX", "MICROSECOND",
      "MICROSECONDS", "MIN", "MINUTE", "MINUTES", "MODE",
      "MODULE", "MONTH", "MONTHS", "NAMED", "NAMES", "NATIONAL",
      "NATURAL", "NCHAR", "NEXT", "NHEADER", "NO", "NOT",
      "NULL", "NULLIF", "NUMERIC", "NUMPARTS", "OBID",
      "OCTET_LENGTH", "OF", "ON", "ONLY", "OPEN", "OPTIMIZE",
      "OPTION", "OR", "ORDER", "OUT", "OUTER", "OUTPUT",
      "OVERLAPS", "PACKAGE", "PAD", "PAGE", "PAGES", "PART",
      "PARTIAL", "PCTFREE", "PCTINDEX", "PLAN", "POSITION",
      "PRECISION", "PREPARE", "PRESERVE", "PRIMARY", "PRIOR",
      "PRIQTY", "PRIVATE", "PRIVILEGES", "PROCEDURE", "PROGRAM",
      "PUBLIC", "READ", "REAL", "REFERENCES", "RELATIVE",
      "RELEASE", "RESET", "RESOURCE", "REVOKE", "RIGHT",
      "RIGHT", "ROLLBACK", "ROW", "ROWS", "RRN", "RUN",
      "SCHEDULE", "SCHEMA", "SCROLL", "SECOND", "SECONDS",
      "SECQTY", "SECTION", "SELECT", "SESSION", "SESSION_USER",
      "SET", "SHARE", "SIMPLE", "SIZE", "SMALLINT", "SOME",
      "SPACE", "SQL", "SQLCODE", "SQLERROR", "SQLSTATE",
      "STATISTICS", "STOGROUP", "STORPOOL", "SUBPAGES",
      "SUBSTRING", "SUM", "SYNONYM", "SYSTEM_USER", "TABLE",
      "TABLESPACE", "TEMPORARY", "THEN", "TIMEZONE_HOUR",
      "TIMEZONE_MINUTE", "TO", "TRAILING", "TRANSACTION",
      "TRANSLATION", "TRIM", "UNION", "UNIQUE", "UNKNOWN",
      "UPDATE", "UPPER", "USAGE", "USER", "USING", "VALIDPROC",
      "VALUE", "VALUES", "VARCHAR", "VARIABLE", "VARYING",
      "VCAT", "VIEW", "VOLUMES", "WHEN", "WHENEVER", "WHERE",
      "WITH", "WORK", "WRITE", "YEAR", "YEARS", "ZONE", "FALSE",
      "TRUE", "absolute", "acquire", "action", "add", "all",
      "allocate", "alter", "and", "any", "are", "as", "asc",
      "assertion", "at", "audit", "authorization", "avg",
      "begin", "between", "bit_length", "both", "bufferpool",
      "by", "call", "capture", "cascaded", "case", "cast",
      "catalog", "ccsid", "char", "char_length", "character",
      "character_length", "check", "close", "cluster",
      "coalesce", "collate", "collation", "collection",
      "column", "comment", "commit", "concat", "connect",
      "connection", "constraint", "constraints", "continue",
      "convert", "corresponding", "count", "create", "cross",
      "current", "current_date", "current_server",
      "current_time", "current_timestamp", "current_timezone",
      "current_user", "cursor", "database", "date", "day",
      "days", "dba", "dbspace", "deallocate", "dec", "decimal",
      "declare", "default", "deferrable", "deferred", "delete",
      "desc", "describe", "descriptor", "diagnostics",
      "disconnect", "distinct", "domain", "double", "drop",
      "editproc", "else", "end", "end-exec", "erase", "escape",
      "except", "exception", "exclusive", "execute", "exists",
      "explain", "external", "extract", "fetch", "fieldproc",
      "first", "float", "for", "foreign", "found", "from",
      "full", "full", "get", "global", "go", "goto", "grant",
      "graphic", "group", "having", "hour", "hours",
      "identified", "identity", "immediate", "in", "index",
      "indicator", "initially", "inner", "inner", "inout",
      "input", "insensitive", "insert", "intersect", "interval",
      "into", "is", "isolation", "join", "join", "key", "label",
      "language", "last", "leading", "left", "left", "level",
      "like", "local", "lock", "locksize", "long", "lower",
      "match", "max", "microsecond", "microseconds", "min",
      "minute", "minutes", "mode", "module", "month", "months",
      "named", "names", "national", "natural", "nchar", "next",
      "nheader", "no", "not", "null", "nullif", "numeric",
      "numparts", "obid", "octet_length", "of", "on", "only",
      "open", "optimize", "option", "or", "order", "out",
      "outer", "output", "overlaps", "package", "pad", "page",
      "pages", "part", "partial", "pctfree", "pctindex", "plan",
      "position", "precision", "prepare", "preserve", "primary",
      "prior", "priqty", "private", "privileges", "procedure",
      "program", "public", "read", "real", "references",
      "relative", "release", "reset", "resource", "revoke",
      "right", "right", "rollback", "row", "rows", "rrn", "run",
      "schedule", "schema", "scroll", "second", "seconds",
      "secqty", "section", "select", "session", "session_user",
      "set", "share", "simple", "size", "smallint", "some",
      "space", "sql", "sqlcode", "sqlerror", "sqlstate",
      "statistics", "stogroup", "storpool", "subpages",
      "substring", "sum", "synonym", "system_user", "table",
      "tablespace", "temporary", "then", "timezone_hour",
      "timezone_minute", "to", "trailing", "transaction",
      "translation", "trim", "union", "unique", "unknown",
      "update", "upper", "usage", "user", "using", "validproc",
      "value", "values", "varchar", "variable", "varying",
      "vcat", "view", "volumes", "when", "whenever", "where",
      "with", "work", "write", "year", "years", "zone", "false",
      "true"};

  public static final String[] predicates = {"UNION", "IS",
      "IS NULL", "NOT NULL", "IS OF", "IN", "BETWEEN",
      "OVERLAPS", "LIKE", "< >", "=", "<", ">", "<=", ">=",
      "NOT", "AND", "OR", "+", "-", "*", "/", "%", "|", ":",
      ".", "[ ]", "::", "SOME", "ANY", "ALL", "EXISTS", "union",
      "is", "is null", "not null", "is of", "in", "between",
      "overlaps", "like", "not", "and", "or", "some", "any",
      "all", "exists"};
  public static final String[] types = {"INTEGER",
      "SMALLINTEGER", "BIGINT", "DECIMAL", "DOUBLE", "REAL",
      "TIME", "TIMESTAMP", "DATE", "DATALINK", "CHAR",
      "VARCHAR", "BLOB", "CLOB", "GRAPHIC", "VARGRAPHIC",
      "DBCLOB", "integer", "smallinteger", "bigint", "decimal",
      "double", "real", "time", "timestamp", "date", "datalink",
      "char", "varchar", "blob", "clob", "graphic",
      "vargraphic", "dbclob"};
  public static final String[] constants = {"FALSE", "NULL",
      "TRUE", "false", "true", "null"};
  public static final String[] functions = {"ABS", "ABSVAL",
      "ACOS", "ASCII", "ASIN", "ATAN", "ATAN2", "BIGINT",
      "BLOB", "CEILING", "CHAR", "CHR", "CLOB", "COALESCE",
      "CONCAT", "CORRELATION", "COS", "COT", "COUNT",
      "COUNT_BIG", "COVARIANCE", "DATE", "DAY", "DAYNAME",
      "DAYOFWEEK", "DAYOFWEEK_ISO", "DAYOFYEAR", "DAYS",
      "DBCLOB", "DECIMAL", "DEGREES", "DEREF", "DIFFERENCE",
      "DIGITS", "DLCOMMENT", "DLLINKTYPE", "DLURLCOMPLETE",
      "DLURLPATH", "DLURLPATHONLY", "DLURLSCHEME",
      "DLURLSERVER", "DLVALUE", "DOUBLE", "EVENT_MON_STATE",
      "EXP", "FLOAT", "FLOOR", "GENERATE_UNIQUE", "GRAPHIC",
      "GROUPING", "HEX", "HOUR", "INSERT", "INTEGER",
      "JULIAN_DAY", "LCASE", "LCASE", "LEFT", "LENGTH", "LN",
      "LOCATE", "LOG", "LOG10", "LONG_VARCHAR",
      "LONG_VARGRAPHIC", "LTRIM", "LTRIM", "MAX", "MICROSECOND",
      "MIDNIGHT_SECONDS", "MIN", "MINUTE", "MOD", "MONTH",
      "MONTHNAME", "NODENUMBER", "NULLIF", "PARTITION",
      "POSSTR", "POWER", "QUARTER", "RADIANS", "RAISE_ERROR",
      "RAND", "REAL", "REPEAT", "REPLACE", "RIGHT", "ROUND",
      "RTRIM", "RTRIM", "SECOND", "SIGN", "SIN", "SMALLINT",
      "SOUNDEX", "SPACE", "SQLCACHE_SNAPSHOT", "SQRT", "STDDEV",
      "SUBSTR", "SUM", "TABLE_NAME", "TABLE_SCHEMA", "TAN",
      "TIME", "TIMESTAMP", "TIMESTAMP_ISO", "TIMESTAMPDIFF",
      "TRANSLATE", "TRUNCATE", "TRUNC", "TYPE_ID", "TYPE_NAME",
      "TYPE_SCHEMA", "UCASE", "UPPER", "VALUE", "VARCHAR",
      "VARGRAPHIC", "VARIANCE", "WEEK", "WEEK_ISO", "YEAR",
      "abs", "absval", "acos", "ascii", "asin", "atan", "atan2",
      "bigint", "blob", "ceiling", "char", "chr", "clob",
      "coalesce", "concat", "correlation", "cos", "cot",
      "count", "count_big", "covariance", "date", "day",
      "dayname", "dayofweek", "dayofweek_iso", "dayofyear",
      "days", "dbclob", "decimal", "degrees", "deref",
      "difference", "digits", "dlcomment", "dllinktype",
      "dlurlcomplete", "dlurlpath", "dlurlpathonly",
      "dlurlscheme", "dlurlserver", "dlvalue", "double",
      "event_mon_state", "exp", "float", "floor",
      "generate_unique", "graphic", "grouping", "hex", "hour",
      "insert", "integer", "julian_day", "lcase", "lcase",
      "left", "length", "ln", "locate", "log", "log10",
      "long_varchar", "long_vargraphic", "ltrim", "ltrim",
      "max", "microsecond", "midnight_seconds", "min", "minute",
      "mod", "month", "monthname", "nodenumber", "nullif",
      "partition", "posstr", "power", "quarter", "radians",
      "raise_error", "rand", "real", "repeat", "replace",
      "right", "round", "rtrim", "rtrim", "second", "sign",
      "sin", "smallint", "soundex", "space",
      "sqlcache_snapshot", "sqrt", "stddev", "substr", "sum",
      "table_name", "table_schema", "tan", "time", "timestamp",
      "timestamp_iso", "timestampdiff", "translate", "truncate",
      "trunc", "type_id", "type_name", "type_schema", "ucase",
      "upper", "value", "varchar", "vargraphic", "variance",
      "week", "week_iso", "year"};
  Object[] allWords = {reservedwords, predicates, types,
      constants, functions};

}