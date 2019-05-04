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
 * Created on Mar 10, 2012
 *
 */
package net.rickcee.swingxs.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import net.miginfocom.swing.MigLayout;
import net.rickcee.swingxs.utils.Utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.validator.routines.EmailValidator;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.JXImagePanel;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTextArea;
import org.jdesktop.swingx.JXTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author RickCeeNet
 * 
 */
public class JXSEmailDialog extends JDialog {
	private static final long serialVersionUID = 1716415635101539101L;
	private static final Logger logger = LoggerFactory.getLogger(JXSEmailDialog.class);

	protected JXLabel toLabel = new JXLabel("To:");
	protected JXLabel ccLabel = new JXLabel("Cc:");
	protected JXLabel subjectLabel = new JXLabel("Subject:");
	protected JXLabel messageLabel = new JXLabel("Message:");

	protected JXTextField toTextField = new JXTextField();
	protected JXTextField ccTextField = new JXTextField();
	protected JXTextField subjectTextField = new JXTextField();
	protected JXTextArea messageTextArea = new JXTextArea();

	protected JXButton sendButton = new JXButton("Send");
	protected JXButton cancelButton = new JXButton("Cancel");

	protected JXImagePanel panel = new JXImagePanel();
	protected EmailValidator emailValidator = EmailValidator.getInstance();
	protected JXSProgressDialog progressDialog = new JXSProgressDialog();
	protected MultiPartEmail multipartEmail;
	protected Thread sender;

	/**
	 * Constructor
	 * 
	 * @param model
	 */
	public JXSEmailDialog() {
		super();
		initialize();
	}

	private void initialize() {
		createGUI();
		createActions();
		setTitle("Email");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setUndecorated(true);
	}

	private void createActions() {
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (multipartEmail == null) {
					JOptionPane.showMessageDialog(null, "Invalid EMail instance. Please contact support.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				if (addRecipients(Message.RecipientType.TO, toTextField)
						&& addRecipients(Message.RecipientType.CC, ccTextField) && processSubjectAndMessage()) {
					sendButton.setEnabled(false);
//					if (sender != null && sender.isAlive()) {
//						// User clicked multiple times
//						return;
//					}

					sender = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								logger.info("Sending Email...");
								multipartEmail.send();
								progressDialog.stopProgress("Email Sent.");
								logger.info("Email Sent.");
							} catch (Exception e) {
								//e.printStackTrace();
								logger.error(e.getMessage(), e);
								progressDialog.stopProgress("An error has occurred!");
								JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							} finally {
								sendButton.setEnabled(true);
								setVisible(false);
							}
						}
					});

					progressDialog.startProgress("Sending Email...");
					sender.start();
				}
			}

		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	/**
	 * Process the values for the subject and body of the email
	 * 
	 * @return
	 */
	private Boolean processSubjectAndMessage() {
		Boolean result = Boolean.FALSE;
		String subject = subjectTextField.getText();
		String message = messageTextArea.getText();
		if (!"".equals(subject)) {
			multipartEmail.setSubject(subject);
			result = Boolean.TRUE;
		} else {
			JOptionPane.showMessageDialog(null, "Subject is not valid, please correct.", "Invalid Subject",
					JOptionPane.ERROR_MESSAGE);
			return result;
		}
		if (!"".equals(message)) {
			try {
				multipartEmail.setMsg(message);
			} catch (EmailException e) {
				e.printStackTrace();
				JXErrorPane.showDialog(e);
			}
			result = Boolean.TRUE;
		} else {
			Integer res = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to send the email with an empty message ?", "Confirm",
					JOptionPane.YES_NO_OPTION);
			if (res.equals(JOptionPane.YES_OPTION)) {
				result = Boolean.TRUE;
			} else {
				result = Boolean.FALSE;
			}
		}
		return result;
	}

	/**
	 * @param type
	 *            The object representing the type of recipient
	 * @param txtField
	 *            The UI object containing the email addresses.
	 * @return
	 */
	private Boolean addRecipients(RecipientType type, JXTextField txtField) {
		StringTokenizer st = new StringTokenizer(txtField.getText(), ",");
		Boolean result = Boolean.FALSE;
		// Blank Field = Tokens: 0
		if (st.countTokens() == 0) {
			String email = txtField.getText();
			result = processEmailAddress(type, email);
		} else {
			while (st.hasMoreTokens()) {
				String email = st.nextToken();
				result = processEmailAddress(type, email);
			}
		}
		return result;
	}

	/**
	 * @param type
	 *            The object representing the type of recipient
	 * @param email
	 *            The string representing the email address of the recipient
	 * @return
	 */
	public Boolean processEmailAddress(RecipientType type, String email) {
		Boolean result = Boolean.FALSE;
		if (emailValidator.isValid(email)) {
			try {
				if (type.equals(Message.RecipientType.TO)) {
					multipartEmail.addTo(email);
					// At least one valid TO address
					result = Boolean.TRUE;
				}
				if (type.equals(Message.RecipientType.CC)) {
					multipartEmail.addCc(email);
					result = Boolean.TRUE;
				}
			} catch (EmailException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (type.equals(Message.RecipientType.CC) && "".equals(email.trim())) {
			// CC is optional
			result = Boolean.TRUE;
		} else {
			JOptionPane.showMessageDialog(null, type + " [" + email + "] doesn't seem to be a valid email address.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}

	private void createGUI() {
		setSize(500, 330);
		setMinimumSize(new Dimension(getSize()));
		setLayout(new MigLayout("wrap 15", "[][grow]", "[][][][grow]"));

		messageTextArea.setMinimumSize(new Dimension(150, 150));
		messageTextArea.setBorder(BorderFactory.createLoweredBevelBorder());

		// progressDialog.setModal(true);
		progressDialog.setUndecorated(true);
		progressDialog.setLocationRelativeTo(null);

		cancelButton.setIcon(Utils.getClassPathImage("images/24/mail_delete.png"));
		sendButton.setIcon(Utils.getClassPathImage("images/24/mail_next.png"));
		panel.setImage(Utils.getClassPathImage("images/64/new_mail_edit.png").getImage());

		add(toLabel);
		add(toTextField, "grow, wrap");
		add(ccLabel);
		add(ccTextField, "grow, wrap");
		add(subjectLabel);
		add(subjectTextField, "grow, wrap");
		add(messageLabel);
		add(messageTextArea, "span 1 2,grow, wrap");
		add(panel, "wrap");
		add(Box.createGlue());
		add(cancelButton, "split 2, align right");
		add(sendButton, "align left");
	}

	public static void main(String args[]) throws Exception {
		JXSEmailDialog dialog = new JXSEmailDialog();
		dialog.multipartEmail = new MultiPartEmail();
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	/**
	 * @return the multipartEmail
	 */
	public MultiPartEmail getMultipartEmail() {
		return multipartEmail;
	}

	/**
	 * @param multipartEmail
	 *            the multipartEmail to set
	 */
	public void setMultipartEmail(MultiPartEmail multipartEmail) {
		this.multipartEmail = multipartEmail;
	}
}
