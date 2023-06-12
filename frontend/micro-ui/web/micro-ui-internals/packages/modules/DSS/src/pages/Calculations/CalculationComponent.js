import React, { useState } from "react";
import { ArrowForward } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";

const romanNumeral = (num) => {
  const romanNumerals = [
    { value: 1000, numeral: "m" },
    { value: 900, numeral: "cm" },
    { value: 500, numeral: "d" },
    { value: 400, numeral: "cd" },
    { value: 100, numeral: "c" },
    { value: 90, numeral: "xc" },
    { value: 50, numeral: "l" },
    { value: 40, numeral: "xl" },
    { value: 10, numeral: "x" },
    { value: 9, numeral: "ix" },
    { value: 5, numeral: "v" },
    { value: 4, numeral: "iv" },
    { value: 1, numeral: "i" },
  ];
  let result = "";
  for (let i = 0; i < romanNumerals.length; i++) {
    while (num >= romanNumerals[i].value) {
      result += romanNumerals[i].numeral;
      num -= romanNumerals[i].value;
    }
  }
  return result;
};

const CalculationComponent = ({ card, index }) => {
  const [isOpen, toggleOpen] = useState(false);
  const { t } = useTranslation();

  const Content = ({ contentObject }) => {
    return (
      <div>
        {contentObject.hasOwnProperty("para") ? (
          <span>{t(contentObject.para)}</span>
        ) : (
          <span style={{ marginLeft: "20px", display: "flex" }}>
            <span>{"•"}</span>
            <span>{t(contentObject.point)}</span>
          </span>
        )}
      </div>
    );
  };

  return (
    <div className="faqs border-none" onClick={() => toggleOpen(!isOpen)}>
      <div className="faq-question" style={{ justifyContent: "space-between", display: "flex" }}>
        <span style={{ fontWeight: 700 }}>{`${index + 1}. ` + t(card.cardName)}</span>
        <span className={isOpen ? "faqicon rotate" : "faqicon"} style={{ float: "right" }}>
          {isOpen ? <ArrowForward /> : <ArrowForward />}
        </span>
      </div>
      <div className="faq-answer" style={isOpen ? { display: "flex", color: "#000" } : { display: "none" }}>
        <div style={{ width: "60%" }}>
          {card.calculation && card.calculation?.map((p) => <Content contentObject={p}></Content>)}
          {card.subCalculation &&
            card.subCalculation?.map((sc, index) => (
              <div style={{ marginLeft: "20px", fontWeight: 600 }}>
                {romanNumeral(index + 1)}. {t(sc.heading)}
                {sc.cals.map((p) => (
                  <Content contentObject={p}></Content>
                ))}
                {sc.hasOwnProperty("nestedCalculation") &&
                  sc.nestedCalculation.map((nsc, index) => (
                    <div style={{ marginLeft: "20px", fontWeight: 600 }}>
                      {index + 1}. {t(nsc.heading)}
                      {nsc.cals.map((p) => (
                        <Content contentObject={p}></Content>
                      ))}
                    </div>
                  ))}
              </div>
            ))}
        </div>
        <div style={{ width: "40%", margin: "auto" }}>
          <img
            src={`${window?.globalConfigs?.getConfig?.("CALCULATION_PAGE_ASSETS")}${card.cardName.toLowerCase()}.png`}
            alt={card.cardName.toLowerCase()}
            style={{ maxWidth: "90%", border: "1px solid #EEEEEE", borderRadius: "4px" }}
          ></img>
        </div>
      </div>
      <div className="cs-box-border" />
    </div>
  );
};
export default CalculationComponent;
