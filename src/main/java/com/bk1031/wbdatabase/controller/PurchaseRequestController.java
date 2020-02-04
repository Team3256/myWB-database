package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.Constants;
import com.bk1031.wbdatabase.model.Event;
import com.bk1031.wbdatabase.model.PurchaseRequest;
import com.google.gson.Gson;
import com.sun.mail.smtp.SMTPTransport;
import io.netty.util.Constant;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static spark.Spark.get;
import static spark.Spark.post;

public class PurchaseRequestController {

    private Connection db;

    Gson gson = new Gson();

    public PurchaseRequestController(Connection db) {
       this.db = db;
       getAllPurchaseRequests();
       createPurchaseRequest();
    }

    private void getAllPurchaseRequests() {
        get("/api/purchase-requests", (request, response) -> {
            List<PurchaseRequest> returnList = new ArrayList<>();
            // Get Events
            String sql = "SELECT * FROM \"purchase_request\"";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                PurchaseRequest pr = new PurchaseRequest();
                pr.setId(rs.getString("id"));
                pr.setSheet(rs.getBoolean("is_sheet"));
                pr.setUserID(rs.getString("user_id"));
                pr.setPartName(rs.getString("part_name"));
                pr.setPartQuantity(rs.getInt("part_quantity"));
                pr.setPartUrl(rs.getString("part_url"));
                pr.setVendor(rs.getString("vendor"));
                pr.setNeedBy(rs.getDate("need_by"));
                pr.setPartNumber(rs.getString("part_number"));
                pr.setCost(rs.getDouble("cost"));
                pr.setTotalCost(rs.getDouble("total_cost"));
                pr.setJustification(rs.getString("justification"));
                pr.setApproved(rs.getBoolean("approved"));
                returnList.add(pr);
            }
            rs.close();
            response.type("application/json");
            response.body(returnList.toString());
            return response;
        });
    }

    private void createPurchaseRequest() {
        post("/api/purchase-requests", (req, res) -> {
            PurchaseRequest pr = gson.fromJson(req.body(), PurchaseRequest.class);
            System.out.println("PARSED PURCHASE REQUEST: " + pr);
            if (pr.toString().contains("null")) {
                res.status(400);
                res.type("application/json");
                res.body("{\"message\": \"Request missing or contains null values\"}");
                return res;
            }
            String existsSql = "SELECT COUNT(1) FROM \"purchase_request\" WHERE id = '" + pr.getId() + "'";
            ResultSet rs = db.createStatement().executeQuery(existsSql);
            while (rs.next()) {
                if (rs.getInt("count") == 1) {
                    res.status(409);
                    res.type("application/json");
                    res.body("{\"message\": \"Purchase request already exists with id: " + pr.getId() + "\"}");
                    return res;
                }
            }
            String sql = "INSERT INTO \"purchase_request\" VALUES " +
                    "(" +
                    "'" + pr.getId() + "'," +
                    "" + pr.isSheet() + "," +
                    "'" + pr.getUserID() + "'," +
                    "'" + pr.getPartName() + "'," +
                    "" + pr.getPartQuantity() + "," +
                    "'" + pr.getPartUrl() + "'," +
                    "'" + pr.getVendor() + "'," +
                    "'" + pr.getNeedBy() + "'," +
                    "'" + pr.getPartNumber() + "'," +
                    "" + pr.getCost() + "," +
                    "" + pr.getTotalCost() + "," +
                    "'" + pr.getJustification() + "'," +
                    "'" + pr.isApproved() + "'" +
                    ")";
            db.createStatement().executeUpdate(sql);
            db.commit();
            System.out.println("Inserted records into the table...");
            // Send PR Email
            System.out.println("TLSEmail Start");
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Constants.prFromEmail, Constants.prFromPassword);
                }
            };
            Session session = Session.getInstance(props, auth);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(Constants.prFromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(Constants.prToEmail));
            msg.addRecipient(Message.RecipientType.CC, new InternetAddress("bharat.kathi@warriorlife.net"));
            msg.addRecipient(Message.RecipientType.CC, new InternetAddress("samuel.stephen@warriorlife.net"));
            msg.setSubject("New Purchase Request!");
            msg.setSentDate(new Date());
            MimeMultipart content = new MimeMultipart();
            MimeBodyPart html = new MimeBodyPart();
            if (pr.isSheet()) {
                html.setContent("<!doctype html>\n" +
                        "<html>\n" +
                        "  <head>\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width\">\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "    <style>\n" +
                        "    @media only screen and (max-width: 620px) {\n" +
                        "      table[class=body] h1 {\n" +
                        "        font-size: 28px !important;\n" +
                        "        margin-bottom: 10px !important;\n" +
                        "      }\n" +
                        "      table[class=body] p,\n" +
                        "            table[class=body] ul,\n" +
                        "            table[class=body] ol,\n" +
                        "            table[class=body] td,\n" +
                        "            table[class=body] span,\n" +
                        "            table[class=body] a {\n" +
                        "        font-size: 16px !important;\n" +
                        "      }\n" +
                        "      table[class=body] .wrapper,\n" +
                        "            table[class=body] .article {\n" +
                        "        padding: 10px !important;\n" +
                        "      }\n" +
                        "      table[class=body] .content {\n" +
                        "        padding: 0 !important;\n" +
                        "      }\n" +
                        "      table[class=body] .container {\n" +
                        "        padding: 0 !important;\n" +
                        "        width: 100% !important;\n" +
                        "      }\n" +
                        "      table[class=body] .main {\n" +
                        "        border-left-width: 0 !important;\n" +
                        "        border-radius: 0 !important;\n" +
                        "        border-right-width: 0 !important;\n" +
                        "      }\n" +
                        "      table[class=body] .btn table {\n" +
                        "        width: 100% !important;\n" +
                        "      }\n" +
                        "      table[class=body] .btn a {\n" +
                        "        width: 100% !important;\n" +
                        "      }\n" +
                        "      table[class=body] .img-responsive {\n" +
                        "        height: auto !important;\n" +
                        "        max-width: 100% !important;\n" +
                        "        width: auto !important;\n" +
                        "      }\n" +
                        "    }\n" +
                        "\n" +
                        "    /* -------------------------------------\n" +
                        "        PRESERVE THESE STYLES IN THE HEAD\n" +
                        "    ------------------------------------- */\n" +
                        "    @media all {\n" +
                        "      .ExternalClass {\n" +
                        "        width: 100%;\n" +
                        "      }\n" +
                        "      .ExternalClass,\n" +
                        "            .ExternalClass p,\n" +
                        "            .ExternalClass span,\n" +
                        "            .ExternalClass font,\n" +
                        "            .ExternalClass td,\n" +
                        "            .ExternalClass div {\n" +
                        "        line-height: 100%;\n" +
                        "      }\n" +
                        "      .apple-link a {\n" +
                        "        color: inherit !important;\n" +
                        "        font-family: inherit !important;\n" +
                        "        font-size: inherit !important;\n" +
                        "        font-weight: inherit !important;\n" +
                        "        line-height: inherit !important;\n" +
                        "        text-decoration: none !important;\n" +
                        "      }\n" +
                        "      #MessageViewBody a {\n" +
                        "        color: inherit;\n" +
                        "        text-decoration: none;\n" +
                        "        font-size: inherit;\n" +
                        "        font-family: inherit;\n" +
                        "        font-weight: inherit;\n" +
                        "        line-height: inherit;\n" +
                        "      }\n" +
                        "      .btn-primary table td:hover {\n" +
                        "        background-color: #34495e !important;\n" +
                        "      }\n" +
                        "      .btn-primary a:hover {\n" +
                        "        background-color: #34495e !important;\n" +
                        "        border-color: #34495e !important;\n" +
                        "      }\n" +
                        "    }\n" +
                        "    </style>\n" +
                        "  </head>\n" +
                        "  <body class=\"\" style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\">\n" +
                        "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\n" +
                        "      <tr>\n" +
                        "        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n" +
                        "        <td class=\"container\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\">\n" +
                        "          <div class=\"content\" style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\">\n" +
                        "\n" +
                        "            <!-- START CENTERED WHITE CONTAINER -->\n" +
                        "            <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;\">\n" +
                        "                        <div align=\"center\">\n" +
                        "                        <img src=\"http://vcrobotics.net/images/WB_Logo_AllBlue.png\" height=\"50\" align=\"center\"></img> </div> \n" +
                        "<br>\n" +
                        "</br>\n" +
                        "              <!-- START MAIN CONTENT AREA -->\n" +
                        "              <tr>\n" +
                        "                <td class=\"wrapper\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\">\n" +
                        "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
                        "                    <tr>\n" +
                        "                      <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">\n" +
                        "                        <h3 align=\"center\"><u>New Purchase Request</u></h3>\n" +
                        "                        <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">The purchase sheet, PDF is accessible below with the blue button!</p>\n" +
                        "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; box-sizing: border-box;\">\n" +
                        "                          <tbody>\n" +
                        "                            <tr>\n" +
                        "                              <td align=\"left\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; padding-bottom: 14px;\">\n" +
                        "                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: auto;\">\n" +
                        "                                  <tbody>\n" +
                        "                                    <tr>\n" +
                        "                                      <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; background-color: #3498db; border-radius: 5px; text-align: center;\"> <a href=\"http://vcrobotics.net/#/purchase-request/view?id=" + pr.getId() + "\" target=\"_blank\" style=\"display: inline-block; color: #ffffff; background-color: #3498db; border: solid 1px #3498db; border-radius: 5px; box-sizing: border-box; cursor: pointer; text-decoration: none; font-size: 14px; font-weight: bold; margin: 0; padding: 12px 25px; text-transform: capitalize; border-color: #3498db;\">Purchase Request Sheet</a> </td>\n" +
                        "                                    </tr>\n" +
                        "                                  </tbody>\n" +
                        "                                </table>\n" +
                        "                        <!-- <p style=\"font-family: sans-serif; font-size: 10px; font-weight: lighter; Margin-bottom:px;\">The link will not go anywhere if there was no purchase request sheet attached.</p> -->\n" +
                        "                              </td>\n" +
                        "                            </tr>\n" +
                        "                          </tbody>\n" +
                        "                        </table>\n" +
                        "                      </td>\n" +
                        "                    </tr>\n" +
                        "                  </table>\n" +
                        "                </td>\n" +
                        "              </tr>\n" +
                        "\n" +
                        "            <!-- END MAIN CONTENT AREA -->\n" +
                        "            </table>\n" +
                        "\n" +
                        "            <!-- START FOOTER -->\n" +
                        "            <div class=\"footer\" style=\"clear: both; Margin-top: 10px; text-align: center; width: 100%;\">\n" +
                        "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
                        "                <tr>\n" +
                        "                  <td class=\"content-block\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\n" +
                        "                    <span class=\"apple-link\" style=\"color: #999999; font-size: 12px; text-align: center;\">WarriorBorgs FRC 3256, 100 Skyway Drive, San Jose CA 95111</span>\n" +
                        "                    <br> PR Request Issue? <a href=\"mailto:bharat.kathi@warriorlife.net?cc=samuel.stephen@warriorlife.net&subject=PR Request Issue&\" style=\"text-decoration: underline; color: #999999; font-size: 12px; text-align: center;\">Contact Us</a>.\n" +
                        "                  </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                  <td class=\"content-block powered-by\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\n" +
                        "                     VC Robotics Systems and Logistics Department\n" +
                        "                  </td>\n" +
                        "                </tr>\n" +
                        "              </table>\n" +
                        "            </div>\n" +
                        "            <!-- END FOOTER -->\n" +
                        "\n" +
                        "          <!-- END CENTERED WHITE CONTAINER -->\n" +
                        "          </div>\n" +
                        "        </td>\n" +
                        "        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n" +
                        "      </tr>\n" +
                        "    </table>\n" +
                        "  </body>\n" +
                        "</html>", "text/html");
            }
            else {
                html.setContent("<!doctype html>\n" +
                        "<html>\n" +
                        "  <head>\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width\">\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "    <style>\n" +
                        "    @media only screen and (max-width: 620px) {\n" +
                        "      table[class=body] h1 {\n" +
                        "        font-size: 28px !important;\n" +
                        "        margin-bottom: 10px !important;\n" +
                        "      }\n" +
                        "      table[class=body] p,\n" +
                        "            table[class=body] ul,\n" +
                        "            table[class=body] ol,\n" +
                        "            table[class=body] td,\n" +
                        "            table[class=body] span,\n" +
                        "            table[class=body] a {\n" +
                        "        font-size: 16px !important;\n" +
                        "      }\n" +
                        "      table[class=body] .wrapper,\n" +
                        "            table[class=body] .article {\n" +
                        "        padding: 10px !important;\n" +
                        "      }\n" +
                        "      table[class=body] .content {\n" +
                        "        padding: 0 !important;\n" +
                        "      }\n" +
                        "      table[class=body] .container {\n" +
                        "        padding: 0 !important;\n" +
                        "        width: 100% !important;\n" +
                        "      }\n" +
                        "      table[class=body] .main {\n" +
                        "        border-left-width: 0 !important;\n" +
                        "        border-radius: 0 !important;\n" +
                        "        border-right-width: 0 !important;\n" +
                        "      }\n" +
                        "      table[class=body] .btn table {\n" +
                        "        width: 100% !important;\n" +
                        "      }\n" +
                        "      table[class=body] .btn a {\n" +
                        "        width: 100% !important;\n" +
                        "      }\n" +
                        "      table[class=body] .img-responsive {\n" +
                        "        height: auto !important;\n" +
                        "        max-width: 100% !important;\n" +
                        "        width: auto !important;\n" +
                        "      }\n" +
                        "    }\n" +
                        "\n" +
                        "    /* -------------------------------------\n" +
                        "        PRESERVE THESE STYLES IN THE HEAD\n" +
                        "    ------------------------------------- */\n" +
                        "    @media all {\n" +
                        "      .ExternalClass {\n" +
                        "        width: 100%;\n" +
                        "      }\n" +
                        "      .ExternalClass,\n" +
                        "            .ExternalClass p,\n" +
                        "            .ExternalClass span,\n" +
                        "            .ExternalClass font,\n" +
                        "            .ExternalClass td,\n" +
                        "            .ExternalClass div {\n" +
                        "        line-height: 100%;\n" +
                        "      }\n" +
                        "      .apple-link a {\n" +
                        "        color: inherit !important;\n" +
                        "        font-family: inherit !important;\n" +
                        "        font-size: inherit !important;\n" +
                        "        font-weight: inherit !important;\n" +
                        "        line-height: inherit !important;\n" +
                        "        text-decoration: none !important;\n" +
                        "      }\n" +
                        "      #MessageViewBody a {\n" +
                        "        color: inherit;\n" +
                        "        text-decoration: none;\n" +
                        "        font-size: inherit;\n" +
                        "        font-family: inherit;\n" +
                        "        font-weight: inherit;\n" +
                        "        line-height: inherit;\n" +
                        "      }\n" +
                        "      .btn-primary table td:hover {\n" +
                        "        background-color: #34495e !important;\n" +
                        "      }\n" +
                        "      .btn-primary a:hover {\n" +
                        "        background-color: #34495e !important;\n" +
                        "        border-color: #34495e !important;\n" +
                        "      }\n" +
                        "    }\n" +
                        "    </style>\n" +
                        "  </head>\n" +
                        "  <body class=\"\" style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\">\n" +
                        "    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\n" +
                        "      <tr>\n" +
                        "        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n" +
                        "        <td class=\"container\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\">\n" +
                        "          <div class=\"content\" style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\">\n" +
                        "\n" +
                        "            <!-- START CENTERED WHITE CONTAINER -->\n" +
                        "            <table class=\"main\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;\">\n" +
                        "                        <div align=\"center\">\n" +
                        "                        <img src=\"http://vcrobotics.net/images/WB_Logo_AllBlue.png\" height=\"50\" align=\"center\"></img> </div> \n" +
                        "<br>\n" +
                        "</br>\n" +
                        "              <!-- START MAIN CONTENT AREA -->\n" +
                        "              <tr>\n" +
                        "                <td class=\"wrapper\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\">\n" +
                        "                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
                        "                    <tr>\n" +
                        "                      <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">\n" +
                        "                        <h3 align=\"center\"><u>New Purchase Request</u></h3>\n" +
                        "                        Part Name: " + pr.getPartName() + "\n" +
                        "                        <br></br>\n" +
                        "                        Part Quantitiy: " + pr.getPartQuantity() + "\n" +
                        "                        <br></br>\n" +
                        "                        <!-- <p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">If a purchase request sheet was filled out, it is accessible below with the blue button!</p> -->\n" +
                        "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; box-sizing: border-box;\">\n" +
                        "                          <tbody>\n" +
                        "                            <tr>\n" +
                        "                                <td align=\"left\" style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; padding-bottom: 14px;\">\n" +
                        "                                  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: auto;\">\n" +
                        "                                    <tbody>\n" +
                        "                                      <tr>\n" +
                        "                                        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; background-color: #3498db; border-radius: 5px; text-align: center;\"> <a href=\"http://vcrobotics.net/#/purchase-request/view?id=" + pr.getId() + "\" target=\"_blank\" style=\"display: inline-block; color: #ffffff; background-color: #3498db; border: solid 1px #3498db; border-radius: 5px; box-sizing: border-box; cursor: pointer; text-decoration: none; font-size: 14px; font-weight: bold; margin: 0; padding: 12px 25px; text-transform: capitalize; border-color: #3498db;\">View Purchase Request</a> </td>\n" +
                        "                                      </tr>\n" +
                        "                                    </tbody>\n" +
                        "                                  </table>\n" +
                        "                          <!-- <p style=\"font-family: sans-serif; font-size: 10px; font-weight: lighter; Margin-bottom:px;\">The link will not go anywhere if there was no purchase request sheet attached.</p> -->\n" +
                        "                                </td>\n" +
                        "                              </tr>\n" +
                        "                          </tbody>\n" +
                        "                        </table>\n" +
                        "                      </td>\n" +
                        "                    </tr>\n" +
                        "                  </table>\n" +
                        "                </td>\n" +
                        "              </tr>\n" +
                        "\n" +
                        "            <!-- END MAIN CONTENT AREA -->\n" +
                        "            </table>\n" +
                        "\n" +
                        "            <!-- START FOOTER -->\n" +
                        "            <div class=\"footer\" style=\"clear: both; Margin-top: 10px; text-align: center; width: 100%;\">\n" +
                        "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\n" +
                        "                <tr>\n" +
                        "                  <td class=\"content-block\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\n" +
                        "                    <span class=\"apple-link\" style=\"color: #999999; font-size: 12px; text-align: center;\">WarriorBorgs FRC 3256, 100 Skyway Drive, San Jose CA 95111</span>\n" +
                        "                    <br> PR Request Issue? <a href=\"mailto:bharat.kathi@warriorlife.net?cc=samuel.stephen@warriorlife.net&subject=PR Request Issue&\" style=\"text-decoration: underline; color: #999999; font-size: 12px; text-align: center;\">Contact Us</a>.\n" +
                        "                  </td>\n" +
                        "                </tr>\n" +
                        "                <tr>\n" +
                        "                  <td class=\"content-block powered-by\" style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\n" +
                        "                     VC Robotics Systems and Logistics Department\n" +
                        "                  </td>\n" +
                        "                </tr>\n" +
                        "              </table>\n" +
                        "            </div>\n" +
                        "            <!-- END FOOTER -->\n" +
                        "\n" +
                        "          <!-- END CENTERED WHITE CONTAINER -->\n" +
                        "          </div>\n" +
                        "        </td>\n" +
                        "        <td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\n" +
                        "      </tr>\n" +
                        "    </table>\n" +
                        "  </body>\n" +
                        "</html>", "text/html");
            }
            content.addBodyPart(html);
            msg.setContent(content);
            SMTPTransport tp = (SMTPTransport) session.getTransport("smtp");
            tp.connect("smtp.gmail.com", Constants.prFromEmail, Constants.prFromPassword);
            tp.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Response: " + tp.getLastServerResponse());
            tp.close();
            // Server Response
            res.type("application/json");
            res.body(pr.toString());
            return res;
        });
    }

}
