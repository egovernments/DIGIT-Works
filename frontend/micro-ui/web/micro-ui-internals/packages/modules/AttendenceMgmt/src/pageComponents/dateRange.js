import React,{ useEffect, useRef, useState } from 'react'
import { DateRange } from 'react-date-range'
import { enGB } from 'date-fns/locale'
import format from 'date-fns/format'
import { addDays } from 'date-fns'

import 'react-date-range/dist/styles.css'
import 'react-date-range/dist/theme/default.css'

const DateRangeComp = () => {

    // date state
    const lastMonday = new Date().setDate(new Date().getDate() - (new Date().getDay() + 6) % 7)
    const [range, setRange] = useState([
        {
            startDate: lastMonday,
            //endDate: addDays(new Date(), 6),
            endDate: addDays(lastMonday, 6),
            key: 'selection'
        }
    ])

    // open close
    const [open, setOpen] = useState(false)

    // get the target element to toggle 
    const refOne = useRef(null)

    useEffect(() => {
        // event listeners
        document.addEventListener("keydown", hideOnEscape, true)
        document.addEventListener("click", hideOnClickOutside, true)
    }, [])

    // hide dropdown on ESC press
    const hideOnEscape = (e) => {
        // console.log(e.key)
        if (e.key === "Escape") {
            setOpen(false)
        }
    }

    // Hide on outside click
    const hideOnClickOutside = (e) => {
        // console.log(refOne.current)
        // console.log(e.target)
        if (refOne.current && !refOne.current.contains(e.target)) {
            setOpen(false)
        }
    }

    // const isDisabledDate = (date) => {
    //   //if it's a not a monday then disable it
    //   date.getDay()===1?false:true;

    // }

    const isDisabled = (date) => {

        return date.getDay() === 1 || date === addDays(range.startDate, 6) ? false : true
    }

    return (
        <div className="field">

            <input
                value={`${format(range[0].startDate, "dd/MM/yyyy")} to ${format(range[0].endDate, "dd/MM/yyyy")}`}
                readOnly
                className="inputBox"
                onClick={() => setOpen(open => !open)}
            />

            <div ref={refOne}>
                {open &&
                    <DateRange
                        locale={enGB}
                        onChange={item => setRange([item.selection])}
                        editableDateInputs={false}
                        moveRangeOnFirstSelection={true}
                        ranges={range}
                        months={1}
                        direction="horizontal"
                        //className="calendarElement"
                        endDateOffset={day => day.add(6, 'days')}
                    />
                }
            </div>

        </div>
    )
}

export default DateRangeComp