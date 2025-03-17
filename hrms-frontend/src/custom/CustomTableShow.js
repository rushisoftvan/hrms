import * as React from 'react';
import {useEffect, useState} from 'react';
import {blockUser, getAllUsers} from '../../service/userService';
import {successToast, warningToast} from '../../toast/toast';
import {formatError} from '../../utils/error';
import {Pagination} from '@mui/lab';
import Switch from '@mui/material/Switch';

import {
  IconButton,
  MenuItem,
  Paper,
  Select,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TableSortLabel,
  TextField,
  Tooltip
} from '@mui/material';
import CustomBackDrop from '../../loader/CustomBackDrop';
import {Delete, EditOutlined, UploadFileOutlined} from '@mui/icons-material';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import Menu from '@mui/material/Menu';
import DeleteUserModal from './DeleteUserModal';
import AddUserModal from './AddUserModal';
import UpdateUserModal from './UpdateUserModal';

// Table columns definition
const columns = [
  {id: 'sr_no', label: '#', minWidth: 170},
  {id: 'user_email', label: 'User Name', minWidth: 170},
  {id: 'first_name', label: 'First Name', minWidth: 170},
  {id: 'last_name', label: 'Last Name', minWidth: 170},
  {id: 'account', label: 'Account', minWidth: 170},
  {id: 'action', label: 'Action', minWidth: 170}
];

export default function Admins() {
  const [order, setOrder] = useState('asc');
  const [orderBy, setOrderBy] = useState('user_email');
  const [page, setPage] = useState(1);
  const [pageSize, setPageSize] = useState(5);
  const [users, setUsers] = useState([]);
  const [totalCount, setTotalCount] = useState(5);
  const [loading, setLoading] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');
  const [showDeleteUserModal, setShowDeleteUserModal] = useState(false);
  const [showAddUserModal, setShowAddUserModal] = useState(false);
  const [showUpdateUserModal, setShowUpdateUserModal] = useState(false);
  const [userData, setUserData] = useState('');

  const [anchorEl, setAnchorEl] = React.useState(null);
  const ITEM_HEIGHT = 48;
  const open = Boolean(anchorEl);
  const handleClick = (event, user) => {
    setUserData({
      firstName: user.first_name,
      lastName: user.last_name,
      password: user.password,
      userId: user._id
    });
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  // useEffect(() => {
  //   getAllUserData();
  // }, [page, pageSize, order, searchQuery]);

  const getAllUserData = (newPage = page, newPageSize = pageSize, searchValue = searchQuery) => {
    setLoading(true);

    const sortBy = {
      [orderBy]: order === 'asc' ? 1 : -1
    };
    getAllUsers(newPage, newPageSize, sortBy, searchValue)
      .then((response) => {
        if (response.status_code === 200 || response.status_code === 201) {
          setLoading(false);
          setUsers(response.data?.content);
          setTotalCount(response.data?.totalCount);
        }
      })
      .catch((e) => {
        warningToast(formatError(e));
        setLoading(false);
      });
  };

  const handleRequestSort = (event, property) => {
    const isAsc = orderBy === property && order === 'asc';
    setOrderBy(property);
    setOrder(isAsc ? 'desc' : 'asc');
    // getAllUserData();
  };

  const handleNextPage = (event, newPage) => {
    setPage(newPage);
  };

  const handlePageSizeChange = (event) => {
    setPageSize(event.target.value);
    setPage(1);
  };

  const handleSearchChange = (event) => {
    setSearchQuery(event.target.value);

  };

  const openDeleteUserModal = () => {
    setShowDeleteUserModal(true);
    handleClose();
  };

  const closeDeleteUserModal = () => {
    setShowDeleteUserModal(false);
  };

  const openAddUserModal = () => {
    setShowAddUserModal(true);
    handleClose();
  };

  const closAddUserModal = () => {
    setShowAddUserModal(false);
  };

  const openUpdateUserModal = () => {
    setShowUpdateUserModal(true);
    handleClose();
  };

  const closeUpdateUserModal = () => {
    setShowUpdateUserModal(false);
  };

  const handleSwitchChange = (event, userId) => {
    const isChecked = event.target.checked

    const requestData = {
      user_id: userId,
      is_block: isChecked
    }

    blockUser(requestData).then((response) => {
      if (response.status_code === 200 || response.status_code === 201) {
        successToast(response.message)
        getAllUserData()
      }
    }).catch((error) => {
      warningToast(formatError(error))
    })
  };

  return (
    <>
      <CustomBackDrop open={loading}/>
      <Paper sx={{width: '100%', overflow: 'hidden'}}>
        <div style={{
          display: 'flex',
          justifyContent: 'end',
          margin: 10
        }}>
          <TextField
            variant="outlined"
            placeholder="Search User Details"
            value={searchQuery}
            onChange={handleSearchChange}
          />
          <Tooltip title="Add User" placement="top">
            <IconButton onClick={openAddUserModal}>
              <UploadFileOutlined
                style={{height: '30px', width: '40px'}}/>
            </IconButton>
          </Tooltip>
        </div>

        <TableContainer sx={{maxHeight: 440}}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                {columns.map((column) => (
                  <TableCell
                    key={column.id}
                    align={column.align}
                    sortDirection={orderBy === column.id ? order : false}
                    style={{minWidth: column.minWidth}}
                  >
                    <TableSortLabel
                      active={orderBy === column.id}
                      direction={orderBy === column.id ? order : 'asc'}
                      onClick={(event) => handleRequestSort(event, column.id)}
                    >
                      {column.label}
                    </TableSortLabel>
                  </TableCell>
                ))}
              </TableRow>
            </TableHead>
            <TableBody>
              {users.length > 0 ? (
                users.map((row, index) => (

                  <TableRow key={row.id}>
                    <TableCell
                      component="th"
                      id={index}
                      scope="row"
                    >
                      {/*{getIndex(page, index)}*/}
                      {index + 1}
                      {/*{startKeyValue === null ? "" : getIndex(page, index)}*/}
                    </TableCell>
                    <TableCell>{row.user_email}</TableCell>
                    <TableCell>{row.first_name}</TableCell>
                    <TableCell>{row.last_name}</TableCell>
                    <TableCell> <Switch checked={row.is_block}
                                        onChange={(event) => handleSwitchChange(event, row._id)}
                                        inputProps={{'aria-label': 'controlled'}}/>
                    </TableCell>
                    <TableCell>
                      <IconButton
                        aria-label="more"
                        id="long-button"
                        aria-controls={open ? 'long-menu' : undefined}
                        aria-expanded={open ? 'true' : undefined}
                        aria-haspopup="true"
                        onClick={(event) => handleClick(event, row)}
                      >
                        <MoreVertIcon/>
                      </IconButton>
                      <Menu
                        id="long-menu"
                        MenuListProps={{
                          'aria-labelledby': 'long-button'
                        }}
                        anchorEl={anchorEl}
                        open={open}
                        onClose={handleClose}
                        slotProps={{
                          paper: {
                            style: {
                              maxHeight: ITEM_HEIGHT * 4.5,
                              width: '20ch'
                            }
                          }
                        }}
                      >
                        <MenuItem
                          onClick={openUpdateUserModal}>
                          <EditOutlined
                            fontSize="small"/> Edit
                        </MenuItem>
                        <MenuItem
                          onClick={openDeleteUserModal}>
                          <Delete
                            fontSize="small"/> Delete
                        </MenuItem>

                      </Menu>
                    </TableCell>
                  </TableRow>
                ))
              ) : (
                <TableRow>
                  <TableCell colSpan={columns.length}
                             align="center">
                    No Data Available
                  </TableCell>
                </TableRow>
              )}

            </TableBody>
          </Table>
        </TableContainer>

        <div style={{
          display: 'flex',
          justifyContent: 'end',
          marginTop: 5,
          marginBottom: 5,
          alignItems: 'center'
        }}>
          <span style={{marginRight: 5}}>Items per page:</span>
          <div>
            <Select value={pageSize}
                    onChange={handlePageSizeChange}>
              <MenuItem value={5}>5</MenuItem>
              <MenuItem value={10}>10</MenuItem>
              <MenuItem value={50}>50</MenuItem>
            </Select>
          </div>
          <Pagination
            count={Math.ceil(totalCount / pageSize)}
            page={page}
            onChange={handleNextPage}
            color="primary"
          />
        </div>
      </Paper>
      <AddUserModal
        open={showAddUserModal}
        close={closAddUserModal}
        setShowAddUserModal={setShowAddUserModal}
        getAllUserData={getAllUserData}
      ></AddUserModal>
      <UpdateUserModal
        open={showUpdateUserModal}
        close={closeUpdateUserModal}
        setShowUpdateUserModal={setShowUpdateUserModal}
        userData={userData}
        getAllUserData={getAllUserData}
      ></UpdateUserModal>
      <DeleteUserModal
        open={showDeleteUserModal}
        close={closeDeleteUserModal}
        setShowDeleteUserModal={setShowDeleteUserModal}
        userData={userData}
      ></DeleteUserModal>
    </>

  );
}
