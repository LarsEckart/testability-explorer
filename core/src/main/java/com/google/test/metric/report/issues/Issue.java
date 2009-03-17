/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.test.metric.report.issues;

/**
 * A model of a single reportable issue with the class under analysis.
 *
 * @author alexeagle@google.com (Alex Eagle)
 */
public class Issue {
  private final int lineNumber;
  private final String elementName;
  private final float contributionToClassCost;

  public Issue(int lineNumber, String elementName, float contributionToClassCost) {
    this.lineNumber = lineNumber;
    this.elementName = elementName;
    this.contributionToClassCost = contributionToClassCost;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public String getElementName() {
    return elementName;
  }

  public float getContributionToClassCost() {
    return contributionToClassCost;
  }

  public enum ConstructionType {
    STATIC_INIT,
    COMPLEXITY,
    STATIC_METHOD,
    NEW_OPERATOR,
    SETTER
  }

  public enum DirectCostType {
    CYCLOMATIC
  }

  public enum CollaboratorType {
    STATIC_METHOD,
    NEW_OPERATOR
  }
}
