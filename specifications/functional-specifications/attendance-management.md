# Attendance Management

## Overview

Attendance Management is divided into 2 parts:

1. Attendance Service
2. Muster-Roll Service

To verify the IDs of the individuals, the attendance service depends on the Individual Registry. It is an optional dependency. If the no individual registry is linked with the attendance service, then the IDs would not be checked for validity and assumed to be correct.&#x20;

## Features & Scope

Attendance Service manages the following:

1. Attendance-Register: It maintains a list of individuals enrolled for a given register.
   * Staff: Staff members manage the register. Staff can be created or deleted from a register.
   * Attendee: Attendees are the individuals participating in the register. Attendees can be created or deleted from the register.&#x20;
2. Attendance-Log: The log entries of the attendance. It will have events of entry and exit.&#x20;

Muster-Roll is a report built upon the attendance logs. It has computed attendance values. It will pass through an approval workflow.&#x20;

