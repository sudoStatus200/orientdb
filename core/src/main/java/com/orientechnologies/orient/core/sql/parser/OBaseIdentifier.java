/* Generated By:JJTree: Do not edit this line. OBaseIdentifier.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.record.OIdentifiable;

import java.util.Map;
import java.util.Set;

public class OBaseIdentifier extends SimpleNode {

  protected OLevelZeroIdentifier levelZero;

  protected OSuffixIdentifier    suffix;

  public OBaseIdentifier(int id) {
    super(id);
  }

  public OBaseIdentifier(OrientSql p, int id) {
    super(p, id);
  }

  /** Accept the visitor. **/
  public Object jjtAccept(OrientSqlVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  public void toString(Map<Object, Object> params, StringBuilder builder) {
    if (levelZero != null) {
      levelZero.toString(params, builder);
    } else if (suffix != null) {
      suffix.toString(params, builder);
    }
  }


  public Object execute(OIdentifiable iCurrentRecord, OCommandContext ctx) {
    if (levelZero != null) {
      return levelZero.execute(iCurrentRecord, ctx);
    }
    if (suffix != null) {
      return suffix.execute(iCurrentRecord, ctx);
    }
    return null;
  }

  public boolean isIndexedFunctionCall() {
    if(levelZero!=null){
      return levelZero.isIndexedFunctionCall();
    }
    return false;
  }

  public long estimateIndexedFunction(OFromClause target, OCommandContext context, OBinaryCompareOperator operator, Object right) {
    if(levelZero!=null){
      return levelZero.estimateIndexedFunction(target, context, operator, right);
    }

    return -1;
  }

  public Iterable<OIdentifiable> executeIndexedFunction(OFromClause target, OCommandContext context,
      OBinaryCompareOperator operator, Object right) {
    if(levelZero!=null){
      return levelZero.executeIndexedFunction(target, context, operator, right);
    }

    return null;
  }

  public boolean isBaseIdentifier() {
    return suffix!=null && suffix.isBaseIdentifier();
  }

  public boolean isExpand() {
    if(levelZero!=null){
      return levelZero.isExpand();
    }
    return false;
  }

  public OExpression getExpandContent() {
    return levelZero.getExpandContent();
  }

  public boolean needsAliases(Set<String> aliases) {
    if(levelZero!=null && levelZero.needsAliases(aliases)){
      return true;
    }
    if(suffix!=null && suffix.needsAliases(aliases)){
      return true;
    }
    return false;
  }
}
/* JavaCC - OriginalChecksum=ed89af10d8be41a83428c5608a4834f6 (do not edit this line) */
