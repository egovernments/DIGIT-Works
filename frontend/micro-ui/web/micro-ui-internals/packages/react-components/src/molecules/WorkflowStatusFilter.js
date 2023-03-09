import React,{Fragment} from 'react'
import CheckBox from "../atoms/CheckBox"
import { Loader } from '../atoms/Loader';

const WorkflowStatusFilter = ({props,t,populators,formData}) => {

    const { data, isLoading } = Digit.Hooks.useApplicationStatusGeneral({ businessServices: [populators.businessService] }, {});

    if(isLoading) return <Loader />

  return (
    <>
        {data?.userRoleStates?.map(row=>{
            return <CheckBox 
                onChange={(e) => {
                    const obj = {
                        ...props.value,
                        [e.target.value]:e.target.checked
                    }
                    props.onChange(obj)
                }}
                value={row.uuid}
                checked={formData?.[populators.name]?.[row.uuid]}
                label={t(`${populators.labelPrefix}${row?.state}`)}
            />
        })}
        {data?.otherRoleStates?.map(row => {
            return <CheckBox
                onChange={(e) => {
                    const obj = {
                        ...props.value,
                        [e.target.value]: e.target.checked
                    }
                    props.onChange(obj)
                }}
                value={row.uuid}
                checked={formData?.[populators.name]?.[row.uuid]}
                label={t(`${populators.labelPrefix}${row?.state}`)}
            />
        })}
    </>
  )
}

export default WorkflowStatusFilter