import { TextInput } from '@egovernments/digit-ui-react-components';
import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useHistory } from 'react-router-dom/cjs/react-router-dom.min';

const SearchBar = (props) => {
    const { t } = useTranslation();
    const history = useHistory();
    const tenantId = Digit.ULBService.getCurrentTenantId()
  const [inputValue, setInputValue] = useState('');
  const [suggestions, setSuggestions] = useState([]);
  const {selectedSOR, setSelectedSOR} = props;

  const fetchData = async (cr) => {
         const requestCriteria = {
            url: '/mdms-v2/v1/_search',
            body: {
                MdmsCriteria: {
                    tenantId: tenantId,
                    moduleDetails: [
            {
                moduleName: "WORKS_SOR",
                masterDetails: [
                    {
                        name: "SOR2",
                        filter:`$[?(@.description=~/.*${cr}.*/i && @.sorType == 'Material')]` 
                    }
                ]
            }
        ]
                },
            },
        };
        try {
            const data  = await Digit.CustomService.getResponse(requestCriteria);
            if(data?.mdmsRes?.WORKS_SOR?.SOR2?.length > 0){
                setSuggestions(data?.mdmsRes?.WORKS_SOR?.SOR2)
            }
        } catch (error) {
            // Handle any errors here
            console.error(error);
        }
};

  useEffect(() => {
    if(inputValue.length > 2){
        fetchData(inputValue);
        console.log(suggestions)
    }else{
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
    <div className={"search-bar-sor"} style={{position: "relative", width: "300px", margin: "20px"}}>
      <TextInput
        type="text"
        name={"Search"}
        placeholder="Search..."
        value={inputValue}
        onChange={handleInputChange}
      />
      <ul className="suggestions-sor" style={{listStyle: "none",
      padding: 0,
    margin: 0,
    position: "absolute",
    width: "100%",
    backgroundColor: "#fff",
    border: "1px solid #ccc",
    borderRadius: "0 0 4px 4px",
    boxShadow: "0px 2px 4px rgba(0, 0, 0, 0.1)"
    }}>
        {suggestions.map((option) => (
          <li
            key={option.id}
            onClick={() => handleSelectOption(option)}
            style={{padding: "10px", cursor: "pointer", transition: "background-color 0.2s ease-in-out" }}
          >
            {option.description}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default SearchBar;
