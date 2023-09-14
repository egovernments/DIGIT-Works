import { Card } from "@egovernments/digit-ui-react-components";
import React, { useState } from "react";




const MeasurementTable = () => {
  const tenantId = Digit.ULBService.getCurrentTenantId();
  const { t } = useTranslation();
  const history = useHistory();


  return (
    <Card>
        View
    </Card>
  );
};

export default MeasurementTable;