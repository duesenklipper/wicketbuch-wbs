/**
 * Copyright 2009 Roland Foerther, Carl-Eric-Menzel, Olaf Siefart
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.senacor.wbs.web.user;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.security.components.markup.html.form.SecureForm;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.senacor.domain.user.User;
import com.senacor.domain.user.UserManager;
import com.senacor.wbs.web.border.LayoutColumn;
import com.senacor.wbs.web.border.ContentLayout;
import com.senacor.wbs.web.border.LayoutColumn.ColumnType;
import com.senacor.wbs.web.border.LayoutColumn.Position;

public class ChangePasswordPanel extends Panel {

 @SpringBean
 private UserManager userManager;
 private ChangePasswordForm changePasswortForm;

 public ChangePasswordPanel(String id, User user) {
  super(id);
  changePasswortForm =
    new ChangePasswordForm("changePasswortForm", user);
  add(changePasswortForm);
 }

 class ChangePasswordForm extends SecureForm {
  private String oldPassword = "";
  private String newPassword1 = "";
  private String newPassword2 = "";
  private User user;

  public ChangePasswordForm(String id, User user) {
   super(id);
   this.user = user;

   ContentLayout subLayout1 = new ContentLayout("sublayout1");
   add(subLayout1);
   subLayout1.add(new LayoutColumn("colOldPwd", ColumnType.c25l,
     Position.LEFT));
   subLayout1.add(new LayoutColumn("colNewPwd1", ColumnType.c25l,
     Position.INTERMEDIATE));
   subLayout1.add(new LayoutColumn("colNewPwd2", ColumnType.c25l,
     Position.INTERMEDIATE));
   subLayout1.add(new LayoutColumn("colAction", ColumnType.c25r,
     Position.RIGHT));

   ContentLayout subLayout2 = new ContentLayout("sublayout2");
   add(subLayout2);


   LayoutColumn column1 =
     new LayoutColumn("colOldPwd", ColumnType.c25l, Position.LEFT);
   column1.add(new PasswordTextField("oldPassword",
     new PropertyModel<String>(this, "oldPassword"))
     .setRequired(true));
   subLayout2.add(column1);

   LayoutColumn column2 =
     new LayoutColumn("colNewPwd1", ColumnType.c25l,
       Position.INTERMEDIATE);
   FormComponent<String> newPassword1FC =
     new PasswordTextField("newPassword1",
       new PropertyModel<String>(this, "newPassword1"))
       .setRequired(true);
   column2.add(newPassword1FC);
   subLayout2.add(column2);

   LayoutColumn column3 =
     new LayoutColumn("colNewPwd2", ColumnType.c25l,
       Position.INTERMEDIATE);
   FormComponent<String> newPassword2FC =
     new PasswordTextField("newPassword2",
       new PropertyModel<String>(this, "newPassword2"))
       .setRequired(true);
   column3.add(newPassword2FC);
   subLayout2.add(column3);

   LayoutColumn column4 =
     new LayoutColumn("colAction", ColumnType.c25r,
       Position.RIGHT);
   column4.add(new Button("changePassword"));
   subLayout2.add(column4);

   add(new EqualPasswordInputValidator(newPassword1FC,
     newPassword2FC));
  }

  @Override
  protected void onSubmit() {
   if (!user.getPasswort().equals(oldPassword)) {
    error("Das eingegebene alte Passwort ist nicht korrekt");
   } else {
    user.setPasswort(newPassword1);
    userManager.saveOrUpdate(user);
    oldPassword = "";
    newPassword1 = "";
    newPassword2 = "";
    info(getString("password.changed"));
   }
  }

 }

}
