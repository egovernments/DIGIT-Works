import { Card, PropertyHouse } from "@egovernments/digit-ui-react-components";
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";

const InboxLinks = ({ parentRoute, businessService }) => {
  const { t } = useTranslation();

  const allLinks = [
    // {
    //   text: "ES_TITLE_NEW_REGISTRATION",
    //   link: "/digit-ui/employee/pt/new-application",
    //   businessService: "PT",
    //   roles: ["PT_CEMP"],
    // },
    {
      text: t("WORKS_CREATE_ESTIMATE"),
      link: `/digit-ui/employee/works/create-estimate`,
      businessService: "WORKS",
      roles: [],
    },
    {
      text: "WORKS_SEARCH_EST",
      link: `/digit-ui/employee/works/search-Estimate`,
      businessService: "WORKS",
      roles: [],
    },
    // { text: "PT_REPORTS", hyperLink: true, link: "/digit-ui/employee/integration/dss/propertytax", roles: [], businessService: "PT" },
    // { text: "PT_DASHBOARD", link: "/digit-ui/employee/", roles: [], businessService: "PT" },
  ];

  const [links, setLinks] = useState([]);

  const { roles: userRoles } = Digit.UserService.getUser().info;

  useEffect(() => {
    let linksToShow = allLinks
      .filter((e) => e.businessService === businessService)
      .filter(({ roles }) => roles.some((e) => userRoles.map(({ code }) => code).includes(e)) || !roles?.length);
    setLinks(linksToShow);
  }, []);

  const GetLogo = () => (
    <div className="header">
      <span className="logo">
        <PropertyHouse />
      </span>{" "}
      <span className="text">{t("WORKS_MGMT")}</span>
    </div>
  );

  return (
    // <Card style={{ paddingRight: 0, marginTop: 0 }} className="employeeCard filter inboxLinks">
    <Card className="employeeCard filter inboxLinks">
      <div className="complaint-links-container">
        {GetLogo()}
        {/* <div style={{ marginLeft: "unset", paddingLeft: "0px" }} className="body"> */}
        <div className="body">
          {allLinks.map(({ link, text, hyperlink = false, roles = [] }, index) => {
            return (
              <span className="link" key={index}>
                {hyperlink ? <a href={link}>{text}</a> : <Link to={link}>{t(text)}</Link>}
              </span>
            );
          })}
        </div>
      </div>
    </Card>
  );
};

export default InboxLinks;
