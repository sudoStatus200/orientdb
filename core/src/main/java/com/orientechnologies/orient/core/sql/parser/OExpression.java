/* Generated By:JJTree: Do not edit this line. OExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.id.ORecordId;

import java.util.Map;
import java.util.Set;

public class OExpression extends SimpleNode {

  protected Boolean singleQuotes;
  protected Boolean doubleQuotes;

  protected boolean isNull = false;
  protected ORid            rid;
  protected OMathExpression mathExpression;
  protected OJson           json;
  protected Boolean         booleanValue;

  public OExpression(int id) {
    super(id);
  }

  public OExpression(OrientSql p, int id) {
    super(p, id);
  }

  /**
   * Accept the visitor.
   **/
  public Object jjtAccept(OrientSqlVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  public Object execute(OIdentifiable iCurrentRecord, OCommandContext ctx) {
    if (isNull) {
      return null;
    }
    if (rid != null) {
      return new ORecordId(rid.cluster.getValue().intValue(), rid.position.getValue().longValue());
    }
    if (mathExpression != null) {
      return mathExpression.execute(iCurrentRecord, ctx);
    }
    if (json != null) {
      return json.toMap(iCurrentRecord, ctx);
    }
    if (booleanValue != null) {
      return booleanValue;
    }
    if (value instanceof ONumber) {
      return ((ONumber) value).getValue();//only for old executor (manually replaced params)
    }


    //from here it's old stuff, only for the old executor
    if (value instanceof ORid) {
      ORid v = (ORid) value;
      return new ORecordId(v.cluster.getValue().intValue(), v.position.getValue().longValue());
    } else if (value instanceof OMathExpression) {
      return ((OMathExpression) value).execute(iCurrentRecord, ctx);
    } else if (value instanceof OJson) {
      return ((OJson) value).toMap(iCurrentRecord, ctx);
    } else if (value instanceof String) {
      return value;
    } else if (value instanceof Number) {
      return value;
    }

    return value;
  }

  public boolean isBaseIdentifier() {
    if (value instanceof OMathExpression) {
      return ((OMathExpression) value).isBaseIdentifier();
    }

    return false;
  }

  public boolean isEarlyCalculated() {
    if (value instanceof Number) {
      return true;
    }
    if (value instanceof String) {
      return true;
    }
    if (value instanceof OMathExpression) {
      return ((OMathExpression) value).isEarlyCalculated();
    }

    return false;
  }

  public OIdentifier getDefaultAlias() {
    OIdentifier identifier = new OIdentifier(-1);
    identifier.setValue(this.toString());
    return identifier;
  }

  public void toString(Map<Object, Object> params, StringBuilder builder) {
//    if (value == null) {
//      builder.append("null");
//    } else if (value instanceof SimpleNode) {
//      ((SimpleNode) value).toString(params, builder);
//    } else if (value instanceof String) {
//      if (Boolean.TRUE.equals(singleQuotes)) {
//        builder.append("'" + value + "'");
//      } else {
//        builder.append("\"" + value + "\"");
//      }
//    } else {
//      builder.append("" + value);
//    }

    if (isNull) {
      builder.append("null");
    } else if (rid != null) {
      rid.toString(params, builder);
    } else if (mathExpression != null) {
      mathExpression.toString(params, builder);
    } else if (json != null) {
      json.toString(params, builder);
    } else if (booleanValue != null) {
      builder.append(booleanValue.toString());
    } else if (value instanceof SimpleNode) {
      ((SimpleNode) value).toString(params, builder);//only for translated input params, will disappear with new executor
    } else if(value instanceof String) {
    if (Boolean.TRUE.equals(singleQuotes)) {
      builder.append("'" + value + "'");
    } else {
      builder.append("\"" + value + "\"");
    }

    } else {
      builder.append(""+value);//only for translated input params, will disappear with new executor
    }
  }

  public static String encode(String s) {
    StringBuilder builder = new StringBuilder(s.length());
    for (char c : s.toCharArray()) {
      if (c == '\n') {
        builder.append("\\n");
        continue;
      }
      if (c == '\t') {
        builder.append("\\t");
        continue;
      }
      if (c == '\\' || c == '"') {
        builder.append("\\");
      }
      builder.append(c);
    }
    return builder.toString();
  }

  public boolean supportsBasicCalculation() {
    if (value instanceof OMathExpression) {
      return ((OMathExpression) value).supportsBasicCalculation();
    }
    return true;
  }

  public boolean isIndexedFunctionCal() {
    if (value instanceof OMathExpression) {
      return ((OMathExpression) value).isIndexedFunctionCall();
    }
    return false;
  }

  public static String encodeSingle(String s) {

    StringBuilder builder = new StringBuilder(s.length());
    for (char c : s.toCharArray()) {
      if (c == '\n') {
        builder.append("\\n");
        continue;
      }
      if (c == '\t') {
        builder.append("\\t");
        continue;
      }
      if (c == '\\' || c == '\'') {
        builder.append("\\");
      }
      builder.append(c);
    }
    return builder.toString();
  }

  public long estimateIndexedFunction(OFromClause target, OCommandContext context, OBinaryCompareOperator operator, Object right) {
    if (value instanceof OMathExpression) {
      return ((OMathExpression) value).estimateIndexedFunction(target, context, operator, right);
    }
    return -1;
  }

  public Iterable<OIdentifiable> executeIndexedFunction(OFromClause target, OCommandContext context,
      OBinaryCompareOperator operator, Object right) {
    if (value instanceof OMathExpression) {
      return ((OMathExpression) value).executeIndexedFunction(target, context, operator, right);
    }
    return null;
  }

  public boolean isExpand() {
    if (mathExpression!=null) {
      return mathExpression.isExpand();
    }
    return false;
  }

  public OExpression getExpandContent() {
    return mathExpression.getExpandContent();
  }

  public boolean needsAliases(Set<String> aliases) {
    if(mathExpression!=null){
      return mathExpression.needsAliases(aliases);
    }
    if(json!=null){
      return json.needsAliases(aliases);
    }
    return false;
  }
}
/* JavaCC - OriginalChecksum=9c860224b121acdc89522ae97010be01 (do not edit this line) */
