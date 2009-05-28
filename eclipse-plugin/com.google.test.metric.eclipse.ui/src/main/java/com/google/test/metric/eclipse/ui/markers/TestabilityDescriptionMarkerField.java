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
package com.google.test.metric.eclipse.ui.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.views.markers.MarkerField;
import org.eclipse.ui.views.markers.MarkerItem;

/**
 * Description column in Testability View.
 * 
 * @author shyamseshadri@google.com (Shyam Seshadri)
 */
public class TestabilityDescriptionMarkerField extends MarkerField {

  public TestabilityDescriptionMarkerField() {
  }

  @Override
  public String getValue(MarkerItem item) {
    return item.getAttributeValue(IMarker.MESSAGE, "");
  }

}
