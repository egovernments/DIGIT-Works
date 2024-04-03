import { Fonts } from "./fonts";
import { CustomService } from "../services/elements/CustomService";

const pdfMake = require("pdfmake/build/pdfmake.js");
// const pdfFonts = require("pdfmake/build/vfs_fonts.js");
// pdfMake.vfs = pdfFonts.pdfMake.vfs;

let pdfFonts = {
  //   Roboto: {
  //     normal: "https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-Regular.ttf",
  //     bold: "https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-Medium.ttf"
  //   },
  Hind: {
    normal: "Hind-Regular.ttf",
    bold: "Hind-Bold.ttf",
  },
  en_IN: {
    normal: "Hind-Regular.ttf",
    bold: "Hind-Bold.ttf",
  },
  pn_IN: {
    normal: "BalooPaaji2-Regular.ttf",
    bold: "BalooPaaji2-Bold.ttf",
  },
  od_IN: {
    normal: "BalooBhaina2-Regular.ttf",
    bold: "BalooBhaina2-Bold.ttf",
  },
  hi_IN: {
    normal: "Hind-Regular.ttf",
    bold: "Hind-Bold.ttf",
  },
};
pdfMake.vfs = Fonts;

pdfMake.fonts = pdfFonts;

const downloadPDFFileUsingBase64 = (receiptPDF, filename) => {
  if (
    window &&
    window.mSewaApp &&
    window.mSewaApp.isMsewaApp &&
    window.mSewaApp.isMsewaApp() &&
    window.mSewaApp.downloadBase64File &&
    window.Digit.Utils.browser.isMobile()
  ) {
    // we are running under webview
    receiptPDF.getBase64((data) => {
      window.mSewaApp.downloadBase64File(data, filename);
    });
  } else {
    // we are running in browser
    receiptPDF.download(filename);
  }
};

function getBase64Image(tenantId) {
  try {
    const img = document.getElementById(`logo-${tenantId}`);
    var canvas = document.createElement("canvas");
    canvas.width = img.width;
    canvas.height = img.height;
    var ctx = canvas.getContext("2d");
    ctx.drawImage(img, 0, 0);
    return canvas.toDataURL("image/png");
  } catch (e) {
    return "";
  }
}

const defaultLogo =
  "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAABBtJREFUWAntV11oVEcUnjN3mzXm111toQ8lWOtLTbIG0YgpMdQ05CEv0iTU1hhR7EMRWYuKSkteVExJBRGkpaUk0ChRSYtpKam4sbE0ia7RSNaS0uIfWtT4E8Xs5u7O8Vy7c3f27t2fh4W+ZF7OmfN9c86Ze2bOvZex/3mAGv/RIU9xJCL2IWIdAS7C/Nyhtbm8l39XeVKfOrLcHZoOHmCA7zJkC6VdSmAQRoYBAHbCXZBzED726xKT0kwgGnyUgpdI0JBEEMD4B+4dV3pU+9Mvyl4NMTZKAV5X7cl0APC5P127BqBNqBwuJ5Gw2G8NbmDIGEcQR+9/u6pAcg0ZYtiRaXCDT75rHnT0bjF0dZgJkLP3VEDVEakcj58ti7MBJOWrPFUHJurUuaGbCVCd5llBdQ4Yw7GnUaM9Fal4JjptJCGGmQA964upnPBXHCYOTSciDMGcp1qnYpzBBXVu6LEEGHxOByViJURJX7m2+W+qmKax3cn4Kk/qdJgnnXOdHXIupZnA/B1jw5TP+wzgngSpLEhX6ahLy/dKm5Su7WODBK4l/I60JZPkJ0DcuvxPLvxr5ZjXUAL45crchxD00A12OR3apTyv/67E7CQerndOztwto9uymPI1N2RwOcMwgBYorigah5qBsN36WVtCCZI9kqqu8Td0DG2mhlJKdb8JGvQOrV86YMevPDZagjpuQoFLqPY3gDtOjawvH7TjZpRAZeelesHwON3jQtUJtej2kdalu1RbZZe/QSB0U6L5ph0AObB9wy0Vn5m2qJI2geWd19yI09eo8SywLjbmdMgaRjZ4+gx9RffV13BGD1BXNV5kCYMzrW641dOvAnGnVgVMHYLUPu2DGxxk4iPJFeFwfbLgL7lcfCi5UqZNgK7WIkm2k4AxHARLyaUSJuBpE6AtBuwCmzaAGM5Tc6neMW7UQdoEcnOdv9Cpv24GjFNAAPCvpalwTuFP1J5vy7kqqRtGOGjfqDZDT5vAQNPbzzTgzQmOAWZotXe4xXNeOj3T9OYTjUMzHU1Le4YQImwdaimndh8/0t4CSV/T83fR1PRUI9W8lALc4jla3x/ryv6UuCqrvh+bp+t6IwL81weQn6abMqFyZnX5BDIugVyQifT52hxD7HyVAFFKb8nreVg46K354bHd2qwn0H6u9i0dI9S2scIMSN8YHHDjnmrfz6YtqmQ1gZ7xxpyJ+5MX6ROYDqplADzPAc2zs/rXv1Qk7TVUyen0iclHDbbBjYWIc3UR3mb1kdUEQGC5NYA6p1dzAp7VBKjulgakhjf+sqwNKoNOGO8i9Uxz8H6KEkzKAvzRimX1Cex+58w/9O2/nT4S4v7/jKDUyo/vrfZ1WxPI6i2Qzvf/VrtKRMJbKewSeiI3aJcn96w++53EVfkCw79XQZYr/EsAAAAASUVORK5CYII=";
const jsPdfGenerator = async ({ breakPageLimit = null, tenantId, logo, name, email, phoneNumber, heading, details, t = (text) => text }) => {

  const emailLeftMargin =
    email.length <= 15
      ? 190
      : email.length <= 20
      ? 150
      : email.length <= 25
      ? 130
      : email.length <= 30
      ? 90
      : email.length <= 35
      ? 50
      : email.length <= 40
      ? 10
      : email.length <= 45
      ? 0
      : email.length <= 50
      ? -20
      : email.length <= 55
      ? -70
      : email.length <= 60
      ? -100
      : -60;

  const dd = {
    pageMargins: [40, 80, 40, 30],
    header: {
      columns: [
        {
          image: logo || getBase64Image(tenantId) || defaultLogo,
          width: 50,
          margin: [10, 10],
        },
        {
          text: name,
          margin: [20, 25],
          font: "Hind",
          fontSize: 14,
          bold: true,
        },
        {
          text: email,
          margin: [emailLeftMargin, 25, 0, 25],
          font: "Hind",
          fontSize: 11,
          color: "#464747",
        },
        {
          text: phoneNumber,
          color: "#6f777c",
          font: "Hind",
          fontSize: 11,
          margin: [-65, 45, 0, 25],
        },
      ],
    },

    footer: function (currentPage, pageCount) {
      return {
        columns: [
          { text: `${name} / ${heading}`, margin: [15, 0, 0, 0], fontSize: 11, color: "#6f777c", width: 400, font: "Hind" },
          { text: `Page ${currentPage}`, alignment: "right", margin: [0, 0, 25, 0], fontSize: 11, color: "#6f777c", font: "Hind" },
        ],
      };
    },
    content: [
      {
        text: heading,
        font: "Hind",
        fontSize: 24,
        bold: true,
        margin: [-25, 5, 0, 0],
      },
      ...createContent(details, phoneNumber, breakPageLimit),
      {
        text: t("PDF_SYSTEM_GENERATED_ACKNOWLEDGEMENT"),
        font: "Hind",
        fontSize: 11,
        color: "#6f777c",
        margin: [-25, 32],
      },
    ],
    defaultStyle: {
      font: "Hind",
    },
  };
  pdfMake.vfs = Fonts;
  let locale = Digit.SessionStorage.get("locale") || "en_IN";
  let Hind = pdfFonts[locale] || pdfFonts["Hind"];
  pdfMake.fonts = { Hind: { ...Hind } };
  const generatedPDF = pdfMake.createPdf(dd);
  downloadPDFFileUsingBase64(generatedPDF, "acknowledgement.pdf");
};


/**
 * Util function that can be used
 * to download WS connection acknowledgement pdfs
 * Data is passed to this function from this file
 * packages\modules\ws\src\utils\getWSAcknowledgementData.js
 * @author nipunarora-egov
 *
 * @example
 * Digit.Utils.pdf.generatev1()
 *
 * @returns Downloads a pdf  
 */
const jsPdfGeneratorv1 = async ({ breakPageLimit = null, tenantId, logo, name, email, phoneNumber, heading, details, headerDetails, t = (text) => text }) => {
  const emailLeftMargin =
    email.length <= 15
      ? 190
      : email.length <= 20
        ? 150
        : email.length <= 25
          ? 130
          : email.length <= 30
            ? 90
            : email.length <= 35
              ? 50
              : email.length <= 40
                ? 10
                : email.length <= 45
                  ? 0
                  : email.length <= 50
                    ? -20
                    : email.length <= 55
                      ? -70
                      : email.length <= 60
                        ? -100
                        : -60;

  const dd = {
    pageMargins: [40, 40, 40, 30],
    header: {},
    footer: function (currentPage, pageCount) {
      return {
        columns: [
          { text: `${name} / ${heading}`, margin: [15, 0, 0, 0], fontSize: 11, color: "#6f777c", width: 400, font: "Hind" },
          { text: `Page ${currentPage}`, alignment: "right", margin: [0, 0, 25, 0], fontSize: 11, color: "#6f777c", font: "Hind" },
        ],
      };
    },
    content: [
      ...createHeader(headerDetails,logo,tenantId),
      // {
      //   text: heading,
      //   font: "Hind",
      //   fontSize: 24,
      //   bold: true,
      //   margin: [-25, 5, 0, 0],
      // },
      ...createContentDetails(details),
      {
        text: t("PDF_SYSTEM_GENERATED_ACKNOWLEDGEMENT"),
        font: "Hind",
        fontSize: 11,
        color: "#6f777c",
        margin: [-25, 32],
      },
    ],
    defaultStyle: {
      font: "Hind",
    },
  };
  
  pdfMake.vfs = Fonts;
  let locale = Digit.SessionStorage.get("locale") || "en_IN";
  let Hind = pdfFonts[locale] || pdfFonts["Hind"];
  pdfMake.fonts = { Hind: { ...Hind } };
  const generatedPDF = pdfMake.createPdf(dd);
  downloadPDFFileUsingBase64(generatedPDF, "acknowledgement.pdf");
};

export default { generate: jsPdfGenerator ,generatev1:jsPdfGeneratorv1};

const createBodyContentBillAmend = (table,t) => {
  let bodyData = []
  bodyData.push({
    text: t(table?.title),
    color: "#F47738",
    style: "header",
    fontSize: 14,
    bold: true,
    margin: [0, 15, 0, 10]
  })
  bodyData.push({
    layout:{
      fillColor:function(rowIndex,node,columnIndex){
        if(rowIndex === (table?.tableRows?.length)) {
          return "#F47738"
        }
        return (rowIndex % 2 === 0) ? "#F47738" : null; 
      },
      fillOpacity:function(rowIndex,node,columnIndex) {
        if (rowIndex === (table?.tableRows?.length)) {
          return 1;
        }
        return (rowIndex % 2 === 0) ? 0.15 : 1;
      }
    },
    table:{
      headerRows:1,
      widths: ["*", "*", "*", "*"],
      body:[
        table?.headers?.map(header =>{
          return {
            text:t(header),
            style:"header",
            fontSize:11,
            bold:true,
            border: [false, false, false, false]
          }
        }),
        ...table?.tableRows?.map(row => {
          return [
            {
              text:t(row?.[0]),
              style:"header",
              fontSize:11,
              border: [false, false, false, false]
            },
            {
              text: t(row?.[1]),
              style: "header",
              fontSize: 11,
              border: [false, false, false, false]
            },
            {
              text: t(row?.[2]),
              style: "header",
              fontSize: 11,
              border: [false, false, false, false]
            },
            {
              text: t(row?.[3]),
              style: "header",
              fontSize: 11,
              border: [false, false, false, false]
            }
          ]
        })
      ]
    }
  })
  return bodyData
}

const createHeaderBillAmend = (headerDetails, logo, tenantId,t) => {
  
  let headerData = [];
  headerData.push({
    style: 'tableExample',
    layout: "noBorders",
    fillColor: "#f7e0d4",
    margin: [-40, -40, -40, 40],
    table: {
      widths: ['5%', 'auto', '*'],
      body: [
        [
            {
            image: logo || getBase64Image(tenantId) || defaultLogo,
            // width: 50,
            margin: [10, 10],
            fit: [50, 50],
            //width: 50,
            //margin: [10, 10]
          },
          {
            text: headerDetails?.header, //"Amritsar Municipal Corporation",
            margin: [40, 10, 2, 4],
            style: "header",
            // italics: true, 
            fontSize: 18,
            bold: true
          },
          {
            text: headerDetails?.typeOfApplication, //"New Sewerage Connection",
            bold: true,
            fontSize: 16,
            alignment: "right",
            margin: [-40, 10, 2, 0],
            color: "#F47738"
          }
        ],
        [
          { text: "" },
          {
            text: headerDetails?.subHeader, //"Municipal Corporation Amritsar, Town Hall, Amritsar, Punjab.",
            margin: [40, -45, -2, -5],
            style: "header",
            // italics: true, 
            fontSize: 10,
            bold: true
          },

          {
            text: headerDetails?.date, //"28/03/2022",
            bold: true,
            fontSize: 16,
            margin: [0, -45, 10, 0],
            alignment: "right",
            color: "#F47738"
          }
        ],
        [
          { text: "" },

          {
            text: headerDetails?.description, //"0183-2545155 | www.amritsarcorp.com | cmcasr@gmail.com",
            margin: [40, -40, 2, 10],
            style: "header",
            // italics: true, 
            fontSize: 10,
            bold: true
          },
          {
            text: "",
          }
        ]
      ]
    }
  });
  headerDetails?.values?.forEach((header, index) => {
    headerData.push({
      style: 'tableExample',
      layout: "noBorders",
      fillColor: "#f7e0d4",
      margin: [-40, -40, -40, 20],
      table: {
        widths: ['30%', '*'],
        body: [
          [
            {
              text: header?.title,
              margin: index == 0 ? [40, 0, 2, 10] : [40, 10, 2, 10],
              style: "header",
              fontSize: 10,
              bold: true
            },
            {
              text: header?.value,
              bold: true,
              fontSize: 10,
              alignment: "left",
              margin: index == 0 ? [0, 0, 2, 10] : [0, 10, 2, 10],
            }
          ]
        ]
      }
    })
  })
  //push demand revision details old way

  headerData.push({
    style: 'tableExample',
    layout: "noBorders",
    fillColor: "#f7e0d4",
    margin: [-40, -25, -1000000, 20],
    table: {
      widths: ['30%', '*'],
      body: [
        [
          {
            text: headerDetails?.DemandRevision?.title,
            margin: [40, 0, 2, 20],
            style: "header",
            fontSize: 13,
            bold: true
          }
        ]
      ]
    }
  })

  headerDetails?.DemandRevision?.values?.forEach((header, index) => {
    headerData.push({
      style: 'tableExample',
      layout: "noBorders",
      fillColor: "#f7e0d4",
      margin: [-40, -40, -40, 20],
      table: {
        widths: ['30%', '*'],
        body: [
          [
            {
              text: header?.title,
              margin: index == 0 ? [40, 0, 2, 10] : [40, 10, 2, 10],
              style: "header",
              fontSize: 10,
              bold: true
            },
            {
              text: header?.value,
              bold: false,
              fontSize: 10,
              alignment: "left",
              margin: index == 0 ? [0, 0, 2, 10] : [0, 10, 2, 10],
            }
          ]
        ]
      }
    })
  })

 //attachment details
  headerData.push({
    style: 'tableExample',
    layout: "noBorders",
    fillColor: "#f7e0d4",
    margin: [-40, -25, -1000000, 20],
    table: {
      widths: ['30%', '*'],
      body: [
        [
          {
            text: headerDetails?.Attachments?.title,
            margin: [40, 0, 2, 110],
            style: "header",
            fontSize: 13,
            bold: true
          }
        ]
      ]
    }
  })
  
  headerData.push({
    layout: "noBorders",
    ul: headerDetails?.Attachments?.values,
    margin:[0,-130,0,40]
   })

  return headerData;
}

const createBodyContent = (details) => {
  let detailsHeaders = []
  details.map((table,index) =>{
    if (table?.isAttachments && table.values) {
      detailsHeaders.push({
        style: 'tableExample',
        layout: "noBorders",
        margin: [0, 13, 0, 5],
        table: {
          body: [
            [
              {
                text: table?.title,
                color: "#F47738",
                style: "header",
                fontSize: 14,
                bold: true
              }
            ]
          ]
        }
      })
      detailsHeaders.push({
        layout:'noBorders',
        ul: table?.values
      })
      return
    }
    detailsHeaders.push({
      layout:'noBorders',
      table:{
        headerRows:1,
        widths:["*","*","*"],
        body:[
          table?.title?.map(t=>{ 
            return {
            text:t,
            color: "#F47738",
            style: "header",
            fontSize: 14,
            bold: true,
            margin:[0,15,0,0]
            }
          }),
          ...table?.values?.map((value,index) => {
            return [
              {
                text:value?.val1,
                style: "header",
                fontSize: 10,
                bold: true
              },
              {
                text: value?.val2,
                fontSize: 10
              },
              {
                text: value?.val3,
                fontSize: 10
              }
            ]
          })
        ]
      }
    })
  })

  return detailsHeaders
}

function createContentDetails(details) {
  let detailsHeaders = [];
  details.forEach((detail, index) => {
    if (detail?.title) {
      detailsHeaders.push({
        style: 'tableExample',
        layout: "noBorders",
        margin:[0,13,0,5],
        table: {
          body: [
            [
              {
                text: detail?.title,
                color: "#F47738",
                style: "header",
                fontSize: 14,
                bold: true
              }
            ]
          ]
        }
      })
    }
    if (detail?.isAttachments && detail.values) {
      detailsHeaders.push({
        ul: detail?.values
      })
    } else {
      detail?.values?.forEach(indData => {
        detailsHeaders.push({
          style: 'tableExample',
          layout: "noBorders",
          table: {
            widths: ['40%', '*'],
            body: [
              [
                {
                  text: indData?.title,
                  style: "header",
                  fontSize: 10,
                  bold: true
                },

                {
                  text: indData?.value,
                  fontSize: 10
                }
              ]
            ]
          }
        })
      })
    }
  });
  return detailsHeaders;
}

function createHeader(headerDetails,logo,tenantId) {
  let headerData = [];
  headerData.push({
    style: 'tableExample',
    layout: "noBorders",
    fillColor: "#f7e0d4",
    "margin": [-40, -40, -40, 40],
    table: {
      widths: ['5%', 'auto', '*'],
      body: [
        [
          // {
          //   margin: [40, 10, 2, 2],
          //   "image": "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAAAXNSR0IArs4c6QAABBtJREFUWAntV11oVEcUnjN3mzXm111toQ8lWOtLTbIG0YgpMdQ05CEv0iTU1hhR7EMRWYuKSkteVExJBRGkpaUk0ChRSYtpKam4sbE0ia7RSNaS0uIfWtT4E8Xs5u7O8Vy7c3f27t2fh4W+ZF7OmfN9c86Ze2bOvZex/3mAGv/RIU9xJCL2IWIdAS7C/Nyhtbm8l39XeVKfOrLcHZoOHmCA7zJkC6VdSmAQRoYBAHbCXZBzED726xKT0kwgGnyUgpdI0JBEEMD4B+4dV3pU+9Mvyl4NMTZKAV5X7cl0APC5P127BqBNqBwuJ5Gw2G8NbmDIGEcQR+9/u6pAcg0ZYtiRaXCDT75rHnT0bjF0dZgJkLP3VEDVEakcj58ti7MBJOWrPFUHJurUuaGbCVCd5llBdQ4Yw7GnUaM9Fal4JjptJCGGmQA964upnPBXHCYOTSciDMGcp1qnYpzBBXVu6LEEGHxOByViJURJX7m2+W+qmKax3cn4Kk/qdJgnnXOdHXIupZnA/B1jw5TP+wzgngSpLEhX6ahLy/dKm5Su7WODBK4l/I60JZPkJ0DcuvxPLvxr5ZjXUAL45crchxD00A12OR3apTyv/67E7CQerndOztwto9uymPI1N2RwOcMwgBYorigah5qBsN36WVtCCZI9kqqu8Td0DG2mhlJKdb8JGvQOrV86YMevPDZagjpuQoFLqPY3gDtOjawvH7TjZpRAZeelesHwON3jQtUJtej2kdalu1RbZZe/QSB0U6L5ph0AObB9wy0Vn5m2qJI2geWd19yI09eo8SywLjbmdMgaRjZ4+gx9RffV13BGD1BXNV5kCYMzrW641dOvAnGnVgVMHYLUPu2DGxxk4iPJFeFwfbLgL7lcfCi5UqZNgK7WIkm2k4AxHARLyaUSJuBpE6AtBuwCmzaAGM5Tc6neMW7UQdoEcnOdv9Cpv24GjFNAAPCvpalwTuFP1J5vy7kqqRtGOGjfqDZDT5vAQNPbzzTgzQmOAWZotXe4xXNeOj3T9OYTjUMzHU1Le4YQImwdaimndh8/0t4CSV/T83fR1PRUI9W8lALc4jla3x/ryv6UuCqrvh+bp+t6IwL81weQn6abMqFyZnX5BDIugVyQifT52hxD7HyVAFFKb8nreVg46K354bHd2qwn0H6u9i0dI9S2scIMSN8YHHDjnmrfz6YtqmQ1gZ7xxpyJ+5MX6ROYDqplADzPAc2zs/rXv1Qk7TVUyen0iclHDbbBjYWIc3UR3mb1kdUEQGC5NYA6p1dzAp7VBKjulgakhjf+sqwNKoNOGO8i9Uxz8H6KEkzKAvzRimX1Cex+58w/9O2/nT4S4v7/jKDUyo/vrfZ1WxPI6i2Qzvf/VrtKRMJbKewSeiI3aJcn96w++53EVfkCw79XQZYr/EsAAAAASUVORK5CYII="
          // },
          {
            image: logo || getBase64Image(tenantId) || defaultLogo,
            // width: 50,
            margin: [10, 10],
            fit: [50,50],
            //width: 50,
            //margin: [10, 10]
          },
          {
            text: headerDetails?.[0]?.header, //"Amritsar Municipal Corporation",
            margin: [40, 10, 2, 4],
            style: "header",
            // italics: true, 
            fontSize: 18,
            bold: true
          },
          {
            text: headerDetails?.[0]?.typeOfApplication, //"New Sewerage Connection",
            bold: true,
            fontSize: 16,
            alignment: "right",
            margin: [-40, 10, 2, 0],
            color: "#F47738"
          }
        ],
        [
          { text: "" },
          {
            text: headerDetails?.[0]?.subHeader, //"Municipal Corporation Amritsar, Town Hall, Amritsar, Punjab.",
            margin: [40, -45, -2, -5],
            style: "header",
            // italics: true, 
            fontSize: 10,
            bold: true
          },

          {
            text: headerDetails?.[0]?.date, //"28/03/2022",
            bold: true,
            fontSize: 16,
            margin: [0, -50, 10, 0],
            alignment: "right",
            color: "#F47738"
          }
        ],
        [
          { text: "" },

          {
            text: headerDetails?.[0]?.description, //"0183-2545155 | www.amritsarcorp.com | cmcasr@gmail.com",
            margin: [40, -40, 2, 10],
            style: "header",
            // italics: true, 
            fontSize: 10,
            bold: true
          },
          {
            text: "",
          }
        ]
      ]
    }
  });
  headerDetails?.[0]?.values?.forEach((header, index) => {
    headerData.push({
      style: 'tableExample',
      layout: "noBorders",
      fillColor: "#f7e0d4",
      "margin": [-40, -40, -40, 20],
      table: {
        widths: ['30%', '*'],
        body: [
          [
            {
              text: header?.title,
              margin: index == 0 ? [40, 0, 2, 10] : [40, 10, 2, 10],
              style: "header",
              fontSize: 10,
              bold: true
            },
            {
              text: header?.value,
              bold: true,
              fontSize: 10,
              alignment: "left",
              margin: index == 0 ? [0, 0, 2, 10] : [0, 10, 2, 10],
            }
          ]
        ]
      }
    })
  })

  return headerData;
}


function createContent(details, phoneNumber, breakPageLimit = null) {
  const data = [];

  details.forEach((detail, index) => {
    if (detail?.values?.length > 0) {
      let column1 = [];
      let column2 = [];

      if ( breakPageLimit ?  (index + 1) % breakPageLimit === 0 : (index + 1) % 7 === 0) {
        data.push({
          text: "",
          margin: [-25, 0, 0, 200],
        });
      }

      data.push({
        text: `${detail.title}`,
        font: "Hind",
        fontSize: 18,
        bold: true,
        margin: [-25, 20, 0, 20],
      });

      const newArray = [];
      let count = 0;
      let arrayNumber = 0;

      detail.values.forEach((value, index) => {
        if (count <= 3) {
          if (!newArray[arrayNumber]) {
            newArray[arrayNumber] = [];
          }
          if (value) {
            newArray[arrayNumber].push(value);
          }
          count++;
        }
        if (count === 4) {
          count = 0;
          arrayNumber++;
        }
      });

      newArray.forEach((value) => {
        if (value?.length === 2) {
          createContentForDetailsWithLengthOfTwo(value, data, column1, column2, detail.values.length > 3 ? 10 : 0);
        } else if (value?.length === 1 || value?.length === 3) {
          createContentForDetailsWithLengthOfOneAndThree(value, data, column1, column2, detail.values.length > 3 ? 10 : 0);
        } else {
          value.forEach((value, index) => {
            let margin = [-25, 0, 0, 5];
            if (index === 1) margin = [15, 0, 0, 5];
            if (index === 2) margin = [26, 0, 0, 5];
            if (index === 3) margin = [30, 0, 0, 5];
            column1.push({
              text: value.title,
              font: "Hind",
              fontSize: 11,
              bold: true,
              margin,
            });
            if (index === 1) margin = [15, 0, 0, 10];
            if (index === 2) margin = [26, 0, 0, 10];
            if (index === 3) margin = [30, 0, 0, 10];
            column2.push({
              text: value.value,
              font: "Hind",
              fontSize: 9,
              margin,
              color: "#1a1a1a",
              width: "25%",
            });
          });
          data.push({ columns: column1 });
          data.push({ columns: column2 });
          column1 = [];
          column2 = [];
        }
      });
    }
  });

  return data;
}

function createContentForDetailsWithLengthOfTwo(values, data, column1, column2, num = 0) {
  values.forEach((value, index) => {
    if (index === 0) {
      column1.push({
        text: value.title,
        font: "Hind",
        fontSize: 12,
        bold: true,
        margin: [-25, num - 10, -25, 0],
      });
      column2.push({
        text: value.value,
        font: "Hind",
        fontSize: 9,
        margin: [-25, 5, 0, 0],
        color: "#1a1a1a",
        width: "25%",
      });
    } else {
      column1.push({
        text: value.title,
        font: "Hind",
        fontSize: 12,
        bold: true,
        margin: [-115, num - 10, -115, 0],
      });
      column2.push({
        text: value.value,
        font: "Hind",
        fontSize: 9,
        margin: [15, 5, 0, 0],
        color: "#1a1a1a",
        width: "25%",
      });
    }
  });
  data.push({ columns: column1 });
  data.push({ columns: column2 });
}

function createContentForDetailsWithLengthOfOneAndThree(values, data, column1, column2, num = 0) {
  values.forEach((value, index) => {
    if (index === 0) {
      column1.push({
        text: value.title,
        font: "Hind",
        fontSize: 12,
        bold: true,
        margin: values.length > 1 ? [-25, -5, 0, 0] : [-25, 0, 0, 0],
      });
      column2.push({
        text: value.value,
        font: "Hind",
        fontSize: 9,
        color: "#1a1a1a",
        margin: values.length > 1 ? [-25, 5, 0, 0] : [-25, 5, 0, 0],
        width: "25%",
      });
    } else if (index === 2) {
      column1.push({
        text: value.title,
        font: "Hind",
        fontSize: 12,
        bold: true,
        margin: [-60, -5, 0, 0],
      });
      column2.push({
        text: value.value,
        font: "Hind",
        fontSize: 9,
        margin: [26, 5, 0, 0],
        color: "#1a1a1a",
        width: "25%",
      });
    } else {
      column1.push({
        text: value.title,
        font: "Hind",
        fontSize: 12,
        bold: true,
        margin: [-28, -5, 0, 0],
      });
      column2.push({
        text: value.value,
        font: "Hind",
        fontSize: 9,
        margin: [15, 5, 0, 0],
        color: "#1a1a1a",
        width: "25%",
      });
    }
  });
  data.push({ columns: column1 });
  data.push({ columns: column2 });
}


const downloadPdf = (blob, fileName) => {
  if (window.mSewaApp && window.mSewaApp.isMsewaApp() && window.mSewaApp.downloadBase64File) {
    var reader = new FileReader();
    reader.readAsDataURL(blob);
    reader.onloadend = function () {
      var base64data = reader.result;
      window.mSewaApp.downloadBase64File(base64data, fileName);
    };
  } else {
    const link = document.createElement("a");
    // create a blobURI pointing to our Blob
    link.href = URL.createObjectURL(blob);
    link.download = fileName;
    // some browser needs the anchor to be in the doc
    document.body.append(link);
    link.click();
    link.remove();
    // in case the Blob uses a lot of memory
    setTimeout(() => URL.revokeObjectURL(link.href), 7000);
  }
};

/* Download Receipts */

export const downloadReceipt = async (
  consumerCode,
  businessService,
  pdfKey = "consolidatedreceipt",
  tenantId = Digit.ULBService.getCurrentTenantId(),
  receiptNumber = null
) => {
  const response = await Digit.ReceiptsService.receipt_download(businessService, consumerCode, tenantId, pdfKey, receiptNumber);
  const responseStatus = parseInt(response.status, 10);
  if (responseStatus === 201 || responseStatus === 200) {
    let filename = receiptNumber ? `receiptNumber-${receiptNumber}.pdf` : `consumer-${consumerCode}.pdf`;
    downloadPdf(new Blob([response.data], { type: "application/pdf" }), filename);
  }
};
/* Download Bills */

export const downloadBill = async (
  consumerCode,
  businessService,
  pdfKey = "consolidatedbill",
  tenantId = Digit.ULBService.getCurrentTenantId(),
) => {
  const response = await Digit.ReceiptsService.bill_download(businessService, consumerCode, tenantId, pdfKey);
  const responseStatus = parseInt(response.status, 10);
  if (responseStatus === 201 || responseStatus === 200) {
    let filename = consumerCode ? `consumerCode-${consumerCode}.pdf` : `consumer-${consumerCode}.pdf`;
    downloadPdf(new Blob([response.data], { type: "application/pdf" }), filename);
  }
};

export const getFileUrl = (linkText = "") => {
  const linkList = (linkText && typeof linkText == "string" && linkText.split(",")) || [];
  let fileURL = "";
  linkList &&
    linkList.map((link) => {
      if (!link.includes("large") && !link.includes("medium") && !link.includes("small")) {
        fileURL = link;
      }
    });
  return fileURL;
};

/* Use this util function to download the file from any s3 links */
export const downloadPDFFromLink = async (link, openIn = "_blank") => {
  var response = await fetch(link, {
    responseType: "arraybuffer",
    headers: {
      "Content-Type": "application/json",
      Accept: "application/pdf",
    },
    method: "GET",
    mode: "cors",
  }).then((res) => res.blob());
  if (window.mSewaApp && window.mSewaApp.isMsewaApp() && window.mSewaApp.downloadBase64File) {
    var reader = new FileReader();
    reader.readAsDataURL(response);
    reader.onloadend = function () {
      var base64data = reader.result;
      window.mSewaApp.downloadBase64File(base64data, decodeURIComponent(link.split("?")[0].split("/").pop().slice(13)));
    };
  } else {
    var a = document.createElement("a");
    document.body.appendChild(a);
    a.style = "display: none";
    let url = window.URL.createObjectURL(response);
    a.href = url;
    a.download = decodeURIComponent(link.split("?")[0].split("/").pop().slice(13));
    a.click();
    window.URL.revokeObjectURL(url);
  }
};

export const getDocumentName = (documentLink = "", index = 0) => {
  let documentName = decodeURIComponent(documentLink.split("?")[0].split("/").pop().slice(13)) || `Document - ${index + 1}`;
  return documentName;
}

/**
 * Custom util to Download any PDF from egov-pdf
 *
 * @author jagankumar-egov
 *
 * @example
 *  Digit.Utils.downloadEgovPDF('project/project-details',{projectId:"",tenantId:""},"project.pdf")
 *
 *  Returns the downloaded pdf
 */
/* Download any PDF from egov-pdf */

export const downloadEgovPDF = async (
  pdfRoute,
  queryParams={},
  fileName="application.pdf"
) => {
  const response =await CustomService.getResponse({ url:`/egov-pdf/download/${pdfRoute}`, params:queryParams, useCache:false,setTimeParam:false ,userDownload:true})
  const responseStatus = parseInt(response.status, 10);
  if (responseStatus === 201 || responseStatus === 200) {
    downloadPdf(new Blob([response.data], { type: "application/pdf" }), fileName);
  }
};