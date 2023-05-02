import React, { Fragment, useState } from "react";
import { CardLabel, LabelFieldPair, LocationSearch, Modal, TextInput } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";

const SelectGeoLocation = ({ onSelect, config, formData }) => {
  const { t } = useTranslation();
  const [showLocationSearch, setShowLocationSearch] = useState(false);
  const [position, setPosition] = useState();
  const onChange = (pincode, position) => {
    setPosition(position);
    onSelect(config?.key, position);
  }

  const handleShowLocationSearch = () => {
    setShowLocationSearch(true);
  } 

  const actionCancelOnSubmit = () =>{
    setShowLocationSearch(false);
  }

  return (
    <Fragment>
      <LabelFieldPair>
        <CardLabel className="card-label bolder">{`${t(`WORKS_GEO_LOCATION`)}`}</CardLabel>
        {
          !showLocationSearch && 
          <div className="field">
            <TextInput customIcon="geolocation" disable={true} onIconSelection={handleShowLocationSearch} value={position?.latitude && position?.longitude ? `${position?.latitude}, ${position?.longitude}` : ''}></TextInput>
          </div>
        }
        {showLocationSearch && 
          <Modal actionCancelLabel={t("WORKS_CANCEL")} actionCancelOnSubmit={actionCancelOnSubmit} hideSubmit={true} >
              <LocationSearch position={{ latitude: formData?.noSubProject_geoLocation?.latitude, longitude: formData?.noSubProject_geoLocation?.longitude }} onChange={onChange} />
          </Modal>
        }
      </LabelFieldPair>
    </Fragment>
  )
}

export default SelectGeoLocation;