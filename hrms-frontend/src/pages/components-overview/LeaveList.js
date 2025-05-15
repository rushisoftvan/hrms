import { useEffect, useState } from 'react';
import { fetchLeaveRequest } from '../../api/leave.api';
import { TableCell, TableRow } from '@mui/material';
import CustomTable from '../../custom/CustomTable';
import AddLeaveModal from './leave/AddLeaveModal';

const LeaveList = () => {
  const [leaveData, setLeaveData] = useState([]);
  const [page, setPage] = useState(0); // Backend page starts from 1, but frontend from 0
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [openAddLeaveModal, setOpenAddLeaveModal] = useState(false);

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
        const response = await fetchLeaveRequest({ pageNo: page + 1 });
        console.log(response.data.data);
        // if (data.totalPages > 0) {
        //   console.log()
        //   const calculatedRowsPerPage = Math.ceil(response.data.totalElements / response.data.totalPages);
        //   setRowsPerPage(calculatedRowsPerPage);
        // }

        // Pass any required parameters here
        setLeaveData((prevState) => {
          // ✅ Correct opening brace
          console.log('Previous leaveData:', prevState);
          console.log('New leaveData:', response.data.data);
          return response.data.data || [];
        });
        const calculatedRowsPerPage = Math.ceil(leaveData.totalElements / leaveData.totalPages);
        setRowsPerPage(calculatedRowsPerPage);
        //console.log('leaveData check', leaveData);
        console.log('row per page', rowsPerPage);
      } catch (error) {
        // console.error('Error fetching leave requests:', error);
      } finally {
        // setLoading(false);
      }
    };

    getLeaveRequests();
  }, [page]);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleOpen = () => setOpenAddLeaveModal(true);
  const handleClose = () => setOpenAddLeaveModal(false);

  const renderHolidayRow = (row, index) => (
    <TableRow key={index} hover>
      {/*<TableCell align="left">{row.holidayName}</TableCell>*/}
      <TableCell
        align="left"
        style={{
          color:
            row.leaveStatusEnum === 'APPROVED'
              ? 'green'
              : ['PENDING', 'REJECTED', 'CANCELED'].includes(row.leaveStatusEnum)
              ? 'red'
              : 'black' // Default color
        }}
      >
        {row.leaveStatusEnum} {/* This ensures the status is visible */}
      </TableCell>

      <TableCell align="left">
        {row.empName}
        <i className="fas fa-sort tooltip-js" id="EmployeeeName" title="Sort" style={{ cursor: 'pointer', marginLeft: '8px' }}></i>
      </TableCell>

      <TableCell align="left">{row.leaveType}</TableCell>
      <TableCell align="left">
        {`${new Date(row.startDate).toLocaleDateString()} - ${new Date(row.endDate).toLocaleDateString()}`}
      </TableCell>
      <TableCell align="left">{new Date(row.dateOfRequest).toLocaleDateString()}</TableCell>
    </TableRow>
  );

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
        <h1>Leave List</h1>
        <button
          style={{
            padding: '8px 16px',
            backgroundColor: '#1976d2',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer'
          }}
          // onClick={() => {
          //   // Handle add leave logic here
          //   console.log('Add Leave clicked');
          // }}
          onClick={handleOpen}
        >
          Add Leave
        </button>
      </div>

      <div>
        <CustomTable
          rows={leaveData.content || []}
          headCells={headCells}
          name="leave list"
          renderHolidayRow={renderHolidayRow}
          totalElement={leaveData.totalElements || 0}
          page={page}
          rowsPerPage={rowsPerPage}
          handleChangePage={handleChangePage}
        ></CustomTable>
      </div>
       <AddLeaveModal openModal={openAddLeaveModal} handleClose={handleClose} />
    </div>
  );
};

export default LeaveList;
