package com.gwdedb.twitchchatlogger.client;

import com.google.gwt.user.cellview.client.CellTable;

public interface TableRes extends CellTable.Resources {
@Source({CellTable.Style.DEFAULT_CSS, "TableStyle.css"})
TableStyle cellTableStyle();
 
interface TableStyle extends CellTable.Style {}
}