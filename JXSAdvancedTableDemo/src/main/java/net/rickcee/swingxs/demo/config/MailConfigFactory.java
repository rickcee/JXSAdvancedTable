/**
 * 
 * Copyright 2009,2010,2011,2012 RickCeeNet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Created on Mar 16, 2012
 *
 */
package net.rickcee.swingxs.demo.config;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import net.rickcee.swingxs.mail.IMailConfig;

/**
 * @author RickCeeNet
 *
 */
public class MailConfigFactory implements IMailConfig {

	private static final String USERNAME = System.getProperty("USERNAME");
	private static final String PASSWORD = System.getProperty("PASSWORD");

	/* (non-Javadoc)
	 * @see net.rickcee.swingxs.mail.IMailConfig#getMailTemplate()
	 */
	@Override
	public MultiPartEmail getMailTemplate() {
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
		email.setTLS(true);
		try {
			email.setFrom("rickcee@gmail.com");
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return email;
	}

}
