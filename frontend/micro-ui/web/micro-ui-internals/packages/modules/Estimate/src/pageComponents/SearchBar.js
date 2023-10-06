import { TextInput } from "@egovernments/digit-ui-react-components";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useHistory } from "react-router-dom/cjs/react-router-dom.min";

const SearchBar = (props) => {
  const { t } = useTranslation();
  const history = useHistory();
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const [inputValue, setInputValue] = useState("");
  const [suggestions, setSuggestions] = useState([]);
  const { selectedSOR, setSelectedSOR, stateData } = props;
  const fetchData = async (cr) => {
    const requestCriteria = {
      url: "/mdms-v2/v1/_search",
      body: {
        MdmsCriteria: {
          tenantId: tenantId,
          moduleDetails: [
            {
              moduleName: "WORKS-SOR",
              masterDetails: [
                {
                  name: "SOR",
                  filter: `$[?(@.description=~/.*${cr}.*/i && @.sorType == '${stateData?.SORType}')]`,
                },
              ],
            },
          ],
        },
      },
    };
    try {
      const data = stateData?.SORType && (await Digit.CustomService.getResponse(requestCriteria));
      if (data?.mdmsRes?.["WORKS-SOR"]?.SOR?.length > 0) {
        setSuggestions(data?.mdmsRes?.["WORKS-SOR"]?.SOR);
      }
    } catch (error) {
      // Handle any errors here
      console.error(error);
    }
  };

  useEffect(() => {
    if (inputValue.length > 2) {
      fetchData(inputValue);
    } else {
      setSuggestions([]);
    }
  }, [inputValue]);

  const handleInputChange = (e) => {
    setInputValue(e.target.value);
  };

  const handleSelectOption = (option) => {
    setInputValue(option.description);
    setSelectedSOR(option);
    setSuggestions([]);
  };

  return (
    <div className={"search-bar-sor"}>
      <TextInput type="text" name={"Search"} placeholder="Search..." value={inputValue} onChange={handleInputChange} customClass="search-sor-input"/>
      <ul
        className="suggestions-sor"
      >
        {suggestions.map((option) => (
          <li
            key={option.id}
            onClick={() => handleSelectOption(option)}
          >
            {option.description}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default SearchBar;
