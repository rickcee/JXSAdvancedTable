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
 * Created on Mar 11, 2012
 *
 */
package net.rickcee.swingxs.mail;

import org.apache.commons.mail.MultiPartEmail;

/**
 * @author RickCeeNet
 * 
 */
public interface IMailConfig {
	/**
	 * Method that is intended to return a MultiPartEmail object template
	 * containing the necessary information to connect to the mail server.
	 * 
	 * @return The object representing the mailhost configuration
	 */
	public MultiPartEmail getMailTemplate();
}
