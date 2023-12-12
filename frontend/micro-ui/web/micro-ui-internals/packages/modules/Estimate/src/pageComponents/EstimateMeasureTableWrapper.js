import React from 'react'

const EstimateMeasureTableWrapper = (props) => {
    let { isCreateRevisionEstimate, isEditRevisionEstimate, revisionNumber } = Digit.Hooks.useQueryParams();
    let MeasureTable = Digit?.ComponentRegistryService?.getComponent("MeasureTable");

  return (
    <MeasureTable 
    isCreateRevisionEstimate={isCreateRevisionEstimate}
    isEditRevisionEstimate={isEditRevisionEstimate}
    revisionNumber={revisionNumber}
    {...props}
    />
  )
}

export default EstimateMeasureTableWrapper