import React from 'react'

const SearchEstimate = () => {

    const onSubmit = (_data) => {
        debugger
        console.log(_data)
    }
    const SearchApplication = Digit.ComponentRegistryService.getComponent("SearchEstimateApplication");
  return (
      <SearchApplication onSubmit={onSubmit}/>
  )
}

export default SearchEstimate