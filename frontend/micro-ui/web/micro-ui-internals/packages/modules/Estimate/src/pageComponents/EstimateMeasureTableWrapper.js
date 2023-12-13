import React from 'react'

const EstimateMeasureTableWrapper = (props) => {
    let { isCreateRevisionEstimate, isEditRevisionEstimate, revisionNumber } = Digit.Hooks.useQueryParams();
    let MeasureTable = Digit?.ComponentRegistryService?.getComponent("MeasureTable");
    if(isCreateRevisionEstimate || isEditRevisionEstimate && props.props["mode"]) props.props["mode"] = "CREATERE"
    if(revisionNumber && props.props["mode"]) props.props["mode"] = "VIEWRE"

  return (
    <MeasureTable 
    {...props}
    />
  )
}

export default EstimateMeasureTableWrapper