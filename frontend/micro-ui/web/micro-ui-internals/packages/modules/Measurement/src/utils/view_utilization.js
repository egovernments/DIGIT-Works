export const sortedFIlteredData = (list, type) => {
  // Function to create the array of objects based on the requirement

  let resultArray = [];

  resultArray = list.filter((item) => item.type === type);

  return resultArray;
};

var multiply = function(num1, num2) {
  if (num1 === '0' || num2 === '0') return '0';

  const m = num1.length, n = num2.length, res = new Array(m + n).fill(0);

  for (let i = m - 1; i >= 0; i--) {
    for (let j = n - 1; j >= 0; j--) {
      const p1 = i + j, p2 = i + j + 1;
      let sum = res[p2] + Number(num1[i]) * Number(num2[j]);
      res[p2] = sum % 10;
      res[p1] += Math.floor(sum / 10);
    }
  }
  while (res[0] === 0) res.shift(); // Remove leading zeros
  return res.length ? res.join('') : '0';
};

const multiplyWithDecimals = (v1, v2) => {
  const getDecimalPlaces = num => {
    return num.includes('.') ? num.split('.')[1].length : 0;
  };

  const d1 = getDecimalPlaces(v1);
  const d2 = getDecimalPlaces(v2);

  // Remove decimals from both numbers
  let num1 = v1.replace('.', '');
  let num2 = v2.replace('.', '');

  // Multiply as whole numbers
  if(num1 === "undefined") num1 = 1;
  if(num2 === "undefined") num2 = 1;
  const result = num1 && num2 && num1 !== "undefined" && num2 !== "undefined" ? (BigInt(num1) * BigInt(num2)).toString() : ""; // Use BigInt for accurate multiplication

  // Insert decimal point at the correct place
  const totalDecimals = d1 + d2;
  if (totalDecimals > 0) {
    const resultLength = result.length;
    const pointPos = resultLength - totalDecimals;

    // Handle cases where result length is shorter than decimal places
    const paddedResult = result.padStart(totalDecimals, '0'); // Ensure enough digits for decimals
    const integerPart = paddedResult.slice(0, pointPos > 0 ? pointPos : 0);
    const fractionalPart = paddedResult.slice(pointPos > 0 ? pointPos : 0);

    // Combine integer and fractional parts
    let formattedResult = (integerPart || '0') + (fractionalPart ? '.' + fractionalPart : '');

    // Remove unnecessary leading and trailing zeros
    formattedResult = parseFloat(formattedResult).toString();

    return formattedResult;
  }

  return result;
};

const roundToPrecisionForString = (value, precision) => {
  // Split the value into integer and decimal parts
  let [integerPart, decimalPart = ''] = value.split('.');

  // If no decimal part or precision is 0, return the integer part
  if (precision === 0 || decimalPart === '') {
    return integerPart;
  }

  // If the decimal part is shorter than the desired precision, return it as is
  if (decimalPart.length <= precision) {
    return value;
  }

  // Extract the portion of the decimal part to consider for rounding
  const roundedDecimal = decimalPart.slice(0, precision);
  const nextDigit = parseInt(decimalPart[precision] || '0', 10);

  let resultDecimal = roundedDecimal;

  // Perform rounding logic
  if (nextDigit >= 5) {
    resultDecimal = (parseInt(roundedDecimal, 10) + 1).toString().padStart(precision, '0');

    // Handle overflow in the decimal part
    if (resultDecimal.length > precision) {
      integerPart = (parseInt(integerPart, 10) + 1).toString();
      resultDecimal = '0'.repeat(precision); // Reset decimal part to zeros
    }
  }

  return `${integerPart}.${resultDecimal}`;
};

export const multiplyFourWithFourPointerPrecision = (v1, v2, v3, v4) => {
  v1 = String(v1);
  v2 = String(v2);
  v3 = String(v3);
  v4 = String(v4);

  let result = multiplyWithDecimals(v1, v2);
  result = multiplyWithDecimals(result, v3);
  result = multiplyWithDecimals(result, v4);
  // console.log("raw", result);
  const totalDecimalsInResult = result.includes('.') ? result.split('.')[1].length : 0
  if (totalDecimalsInResult > 4) {
    result = roundToPrecisionForString(result, 4);
  }
  // console.log("adjusted", result);
  return parseFloat(result);
}
