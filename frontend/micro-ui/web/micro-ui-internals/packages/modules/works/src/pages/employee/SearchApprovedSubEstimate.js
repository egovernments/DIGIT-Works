import React from 'react'

const SearchApprovedSubEstimate = () => {

    const onSubmit = (_data) => {
        debugger
        console.log(_data)
    }
    const SearchApplicationApproved = Digit.ComponentRegistryService.getComponent("SearchApprovedSubEs");
    return (
        <SearchApplicationApproved onSubmit={onSubmit} />
    )
}

export default SearchApprovedSubEstimate