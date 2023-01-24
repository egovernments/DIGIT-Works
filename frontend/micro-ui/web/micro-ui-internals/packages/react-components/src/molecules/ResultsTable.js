import React from 'react'
import { useTranslation } from 'react-i18next';
import DetailsCard from './DetailsCard';


const searchResult = [
  {
    firstname:"Nipun",
    lastname:"Arora",
    age:"23",
    gender:"male",
    nationality:"indian"
  }
]

const tableConfig = {}

//implement global search, sorting by columns , and colored links(thru props) , pagination

const ResultsTable = ({config}) => {
  const { t } = useTranslation()
  const isMobile = window.Digit.Utils.browser.isMobile();
  return (
    <div>ResultsTable</div>
  )
}

export default ResultsTable