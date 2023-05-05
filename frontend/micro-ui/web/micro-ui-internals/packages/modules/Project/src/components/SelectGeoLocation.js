import React, { Fragment, useState } from "react";
import { CardLabel, LabelFieldPair, LocationSearch, Modal, TextInput } from "@egovernments/digit-ui-react-components";
import { useTranslation } from "react-i18next";

const SelectGeoLocation = ({ onSelect, config, formData }) => {
  const { t } = useTranslation();
  const [showLocationSearch, setShowLocationSearch] = useState(false);
  const [position, setPosition] = useState();
  const [postionInputField, setPositionInputField] = useState();
  const onChange = (pincode, position) => {
    setPosition(position);
  }

  const handleShowLocationSearch = () => {
    setShowLocationSearch(true);
  } 

  const actionCancelOnSubmit = () =>{
    setShowLocationSearch(false);
  }

  const actionSaveOnSubmit = () => {
    onSelect(config?.key, position);
    setPositionInputField(position);
    actionCancelOnSubmit();
  }

  return (
    <Fragment>
      <LabelFieldPair>
        <CardLabel className="card-label bolder">{`${t(`WORKS_GEO_LOCATION`)}`}</CardLabel>
        {
          !showLocationSearch && 
          <div className="field">
            <TextInput customIcon="geolocation" disable={true} onIconSelection={handleShowLocationSearch} value={postionInputField?.latitude && postionInputField?.longitude ? `${position?.latitude}, ${position?.longitude}` : ''}></TextInput>
          </div>
        }
        {showLocationSearch && 
          <Modal actionCancelLabel={t("WORKS_CANCEL")} actionSaveLabel={t("WORKS_APPLY")} actionCancelOnSubmit={actionCancelOnSubmit} actionSaveOnSubmit={actionSaveOnSubmit} hideSubmit={false} >
              <LocationSearch position={{ latitude: formData?.noSubProject_geoLocation?.latitude, longitude: formData?.noSubProject_geoLocation?.longitude }} onChange={onChange} />
          </Modal>
        }
      </LabelFieldPair>
    </Fragment>
  )
}

export default SelectGeoLocation;