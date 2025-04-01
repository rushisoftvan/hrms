import { useEffect, useState } from 'react';
import { fetchLeaveRequest } from '../../api/leave.api';
import { TableCell, TableRow } from '@mui/material';
import CustomTable from '../../custom/CustomTable';

const LeaveList = () => {
  const [leaveData, setLeaveData] = useState([]);

  const headCells = [
    {
      id: 'Status',
      align: 'left',
      disablePadding: false,
      label: 'Status'
    },

    {
      id: 'Employee Name',
      align: 'left',
      disablePadding: false,
      label: 'Employee Name'
    },
    {
      id: 'Leave type',
      align: 'left',
      disablePadding: true,
      label: 'Leave type'
    },
    {
      id: 'Leave period',
      align: 'left',
      disablePadding: false,
      label: 'Leave period'
    },
    {
      id: 'Date Of Request',
      align: 'left',
      disablePadding: false,
      label: 'Date Of Request'
    }
  ];

  useEffect(() => {
    const getLeaveRequests = async () => {
      try {
        const response = await fetchLeaveRequest({"pageNo" : 1});
        console.log(response.data.data);
        // Pass any required parameters here
        setLeaveData(prevState => {  // ✅ Correct opening brace
          console.log("Previous leaveData:", prevState);
          console.log("New leaveData:", response.data.data);
          return response.data.data || [];
        });
      } catch (error) {
        // console.error('Error fetching leave requests:', error);
      } finally {
        // setLoading(false);
      }
    };

    getLeaveRequests();
  }, []);

  const renderHolidayRow = (row, index) => (
    <TableRow key={index} hover>
      {/*<TableCell align="left">{row.holidayName}</TableCell>*/}
      <TableCell
        align="left"
        style={{
          color: row.leaveStatusEnum === "APPROVED" ? "green"
            : ["PENDING", "REJECTED", "CANCELED"].includes(row.leaveStatusEnum) ? "red"
              : "black" // Default color
        }}
      >
        {row.leaveStatusEnum} {/* This ensures the status is visible */}
      </TableCell>


      <TableCell align="left">
        {row.empName}
        <i
          className="fas fa-sort tooltip-js"
          id="EmployeeeName"
          title="Sort"
          style={{ cursor: "pointer", marginLeft: "8px" }}
        ></i>
      </TableCell>

      <TableCell align="left">{row.leaveType}</TableCell>
      <TableCell align="left">
        {`${new Date(row.startDate).toLocaleDateString()} - ${new Date(row.endDate).toLocaleDateString()}`}
      </TableCell>
    </TableRow>
  );

  return (

    <div>
      <h1>Leave List</h1>
      <div>
        <CustomTable rows = {leaveData.content}  headCells={headCells} name = {"leave list"} renderHolidayRow = {renderHolidayRow}  ></CustomTable>
      </div>
    </div>
  );
};

export default LeaveList;
