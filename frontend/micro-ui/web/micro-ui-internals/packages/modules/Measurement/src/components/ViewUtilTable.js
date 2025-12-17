import { AddIcon, TextInput, Amount, Button, Dropdown, Loader, DeleteIcon, TextArea } from "@egovernments/digit-ui-react-components";

import React, { Fragment, useEffect, useCallback } from "react";
import { useTranslation } from "react-i18next";

let defaultSOR = {
  amount: 0,
  consumedQ: 0,
  sNo: 1,
  uom: null,
  description: "",
  unitRate: 0,
  category: "NON-SOR",
  targetId: null,
  approvedQuantity: 0,
  currentMBEntry: 0,
  measures: [
    {
      sNo: 1,
      targetId: null,
      isDeduction: false,
      description: null || "   ",
      id: 0,
      height: 0,
      width: 0,
      length: 0,
      number: 0,
      noOfunit: 0,
      rowAmount: 0,
      consumedRowQuantity: 0,
    },
  ],
};

function hasDecimalPlaces(number, decimalPlaces) {
  if (number == "") {
    return true;
  }
  var numStr = number.toString();
  // Using regex to check if its accepting upto given decimal places
  var regex = new RegExp(`^[0-9]+(\\.[0-9]{0,${decimalPlaces}})?$`);
  return regex.test(numStr);
}
