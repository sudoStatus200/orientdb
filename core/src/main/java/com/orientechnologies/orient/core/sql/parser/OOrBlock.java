/* Generated By:JJTree: Do not edit this line. OOrBlock.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.impl.ODocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OOrBlock extends OBooleanExpression {
  List<OBooleanExpression> subBlocks = new ArrayList<OBooleanExpression>();

  public OOrBlock(int id) {
    super(id);
  }

  public OOrBlock(OrientSql p, int id) {
    super(p, id);
  }

  @Override
  public boolean evaluate(OIdentifiable currentRecord, OCommandContext ctx) {
    if (getSubBlocks() == null) {
      return true;
    }

    for (OBooleanExpression block : subBlocks) {
      if (block.evaluate(currentRecord, ctx)) {
        return true;
      }
    }
    return false;
  }

  public boolean evaluate(Object currentRecord, OCommandContext ctx) {
    if (currentRecord instanceof OIdentifiable) {
      return evaluate((OIdentifiable) currentRecord, ctx);
    } else if (currentRecord instanceof Map) {
      ODocument doc = new ODocument();
      doc.fromMap((Map<String, Object>) currentRecord);
      return evaluate(doc, ctx);
    }
    return false;
  }

  public List<OBooleanExpression> getSubBlocks() {
    return subBlocks;
  }

  public void setSubBlocks(List<OBooleanExpression> subBlocks) {
    this.subBlocks = subBlocks;
  }

  public void toString(Map<Object, Object> params, StringBuilder builder) {
    if (subBlocks == null || subBlocks.size() == 0) {
      return;
    }
    // if (subBlocks.size() == 1) {
    // subBlocks.get(0).toString(params, builder);
    // return;
    // }

    boolean first = true;
    for (OBooleanExpression expr : subBlocks) {
      if (!first) {
        builder.append(" OR ");
      }
      expr.toString(params, builder);
      first = false;
    }
  }

  @Override
  protected boolean supportsBasicCalculation() {
    for (OBooleanExpression expr : subBlocks) {
      if (!expr.supportsBasicCalculation()) {
        return false;
      }
    }
    return true;
  }

  @Override
  protected int getNumberOfExternalCalculations() {
    int result = 0;
    for (OBooleanExpression expr : subBlocks) {
      result += expr.getNumberOfExternalCalculations();
    }
    return result;
  }

  @Override
  protected List<Object> getExternalCalculationConditions() {
    List<Object> result = new ArrayList<Object>();
    for (OBooleanExpression expr : subBlocks) {
      result.addAll(expr.getExternalCalculationConditions());
    }
    return result;
  }

  public List<OBinaryCondition> getIndexedFunctionConditions(OClass iSchemaClass, ODatabaseDocumentInternal database) {
    if (subBlocks == null || subBlocks.size() > 1) {
      return null;
    }
    List<OBinaryCondition> result = new ArrayList<OBinaryCondition>();
    for (OBooleanExpression exp : subBlocks) {
      List<OBinaryCondition> sub = exp.getIndexedFunctionConditions(iSchemaClass, database);
      if (sub != null && sub.size() > 0) {
        result.addAll(sub);
      }
    }
    return result.size() == 0 ? null : result;
  }

  public List<OAndBlock> flatten() {
    List<OAndBlock> result = new ArrayList<OAndBlock>();
    for(OBooleanExpression sub:subBlocks){
      List<OAndBlock> childFlattened = sub.flatten();
      for(OAndBlock child:childFlattened){
        result.add(child);
      }
    }
    return result;
  }

  @Override public boolean needsAliases(Set<String> aliases) {
    for(OBooleanExpression expr:subBlocks){
      if(expr.needsAliases(aliases)){
        return true;
      }
    }
    return false;
  }

}
/* JavaCC - OriginalChecksum=98d3077303a598705894dbb7bd4e1573 (do not edit this line) */
