var mustache = require("mustache");

var { directMapping } = require("./directMapping");
var { externalAPIMapping } = require("./externalAPIMapping");

var { getValue, getDateInRequiredFormat } = require("./commons");

mustache.escape = function (text) {
  return text;
};

const updateBorderlayout = (formatconfig) => {
  formatconfig.content = formatconfig.content.map((item) => {
    if (
      item.hasOwnProperty("layout") &&
      typeof item.layout === "object" &&
      Object.keys(item.layout).length === 0
    ) {
      item.layout = borderLayout;
    }
    return item;
  });
  return formatconfig;
};

/**
 *
 * @param {*} variableTovalueMap - key, value map. Keys are variable defined in data config
 * and value is their corresponding values. Map will be used by Moustache template engine
 * @param {*} formatconfig -format config read from formatconfig file
 */
export const fillValues = (variableTovalueMap, formatconfig) => {
  let input = JSON.stringify(formatconfig).replace(/\\/g, "");

  //console.log(variableTovalueMap);
  //console.log(mustache.render(input, variableTovalueMap).replace(/""/g,"\"").replace(/"\[/g,"\[").replace(/\]"/g,"\]").replace(/\]\[/g,"\],\[").replace(/"\{/g,"\{").replace(/\}"/g,"\}"));
  let output = JSON.parse(
    mustache
      .render(input, variableTovalueMap)
      .replace(/""/g, '""')
      //.replace(/\\/g, "")
      .replace(/"\[/g, "[")
      .replace(/\]"/g, "]")
      .replace(/\]\[/g, "],[")
      .replace(/"\{/g, "{")
      .replace(/\n/g, "\\n")
      .replace(/\t/g, "\\t")
  );
  return output;
};

/**
 * generateQRCodes-function to geneerate qrcodes
 * moduleObject-current module object from request body
 * dataconfig- data config read from dataconfig of module
 */
const generateQRCodes = async (
  moduleObject,
  dataconfig,
  variableTovalueMap
) => {
  // let qrcodeMappings = getValue(
  //   jp.query(dataconfig, "$.DataConfigs.mappings.*.mappings.*.qrcodeConfig.*"),
  //   [],
  //   "$.DataConfigs.mappings.*.mappings.*.qrcodeConfig.*"
  // );
  // // for (var i = 0, len = qrcodeMappings.length; i < len; i++) {
  // //   let qrmapping = qrcodeMappings[i];
  // // //   let varname = qrmapping.variable;
  // // //   let qrtext = mustache.render(qrmapping.value, variableTovalueMap);
  // // // //   let qrCodeImage = await QRCode.toDataURL(qrtext);
  // // // //   variableTovalueMap[varname] = qrCodeImage;
  // // }
};

const handleDerivedMapping = (dataconfig, variableTovalueMap) => {
  let derivedMappings = getValue(
    jp.query(dataconfig, "$.DataConfigs.mappings.*.mappings.*.derived.*"),
    [],
    "$.DataConfigs.mappings.*.mappings.*.derived.*"
  );

  for (var i = 0, len = derivedMappings.length; i < len; i++) {
    let mapping = derivedMappings[i];
    let expression = mustache
      .render(
        mapping.formula.replace(/-/g, " - ").replace(/\+/g, " + "),
        variableTovalueMap
      )
      .replace(/NA/g, "0");
    let type = mapping.type;
    let format = mapping.format;
    let variableValue = Function(`'use strict'; return (${expression})`)();
    if (type == "date") {
      let myDate = new Date(variableValue);
      if (isNaN(myDate) || variableValue === 0) {
        variableValue = "NA";
      } else {
        let replaceValue = getDateInRequiredFormat(variableValue, format);
        variableValue = replaceValue;
      }
    }
    variableTovalueMap[mapping.variable] = variableValue;
  }
};
const unregisteredLocalisationCodes=[];
export const handlelogic = async (
  key,
  formatObject,
  moduleObject,
  dataconfig,
  isCommonTableBorderRequired,
  requestInfo
) => {
  let variableTovalueMap = {};
  //direct mapping service
  await Promise.all([
    directMapping(
      moduleObject,
      dataconfig,
      variableTovalueMap,
      requestInfo,
      unregisteredLocalisationCodes,
      key
    ),
    //external API mapping
    externalAPIMapping(
      key,
      moduleObject,
      dataconfig,
      variableTovalueMap,
      requestInfo,
      unregisteredLocalisationCodes
    ),
  ]);
  // await generateQRCodes(moduleObject, dataconfig, variableTovalueMap);
  handleDerivedMapping(dataconfig, variableTovalueMap);
  console.log(variableTovalueMap,'variableTovalueMap');
  formatObject = fillValues(variableTovalueMap, formatObject);
  if (isCommonTableBorderRequired === true)
    formatObject = updateBorderlayout(formatObject);
  return formatObject;
};
