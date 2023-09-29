// ViewComposer.js
import React from 'react';
import { Loader, Card } from '@egovernments/digit-ui-react-components';

const ViewComposer = ({ isLoading, cards, activeNav, renderCardSectionJSX }) => {
  if (isLoading) return <Loader />;

  return (
    <div className={"employee-main-application-details"}>
      {cards.map((card, cardIdx) => {
        const { sections } = card;
        return (
          <Card
            key={cardIdx}
            style={activeNav && card.navigationKey ? (activeNav !== card.navigationKey ? { display: "none" } : {}) : {}}
            className={"employeeCard-override"}
          >
            {sections.map((section, sectionIdx) => {
              return renderCardSectionJSX(section);
            })}
          </Card>
        );
      })}
    </div>
  );
};

export default ViewComposer;
