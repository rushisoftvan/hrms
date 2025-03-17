import React from 'react';
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';
// Ensure this component is correctly handling sorting
// import OrderStatus from "./OrderStatus"; // Ensure this component is correctly handling status

export default function CustomTable({ rows }) {

  console.log('props' +  rows);
  // const [order, setOrder] = useState("asc");
  // const [orderBy, setOrderBy] = useState("trackingNo");
  // const [selected, setSelected] = useState([]);

  // const handleRequestSort = (event, property) => {
  //   const isAsc = orderBy === property && order === "asc";
  //   setOrder(isAsc ? "desc" : "asc");
  //   setOrderBy(property);
  // };

  const headCells = [
    {
      id: 'Holiday Name',
      align: 'left',
      disablePadding: false,
      label: 'Holiday Name'
    },
    {
      id: 'holidayDate',
      align: 'left',
      disablePadding: true,
      label: 'holidayDate'
    },
    {
      id: 'isOptional',
      align: 'left',
      disablePadding: false,
      label: 'isOptional'
    },
    {
      id: 'holidayDay',
      align: 'left',
      disablePadding: false,

      label: 'holidayDay'
    }
  ];

  // const isSelected = (trackingNo) => selected.indexOf(trackingNo) !== -1;

  return (

    <Box>
      <TableContainer
        sx={{
          width: "100%",
          overflowX: "auto",
          position: "relative",
          display: "block",
          maxWidth: "100%",
          "& td, & th": { whiteSpace: "nowrap" },
        }}
      >
        <Table
          aria-labelledby="tableTitle"
          sx={{
            "& .MuiTableCell-root:first-of-type": { pl: 2 },
            "& .MuiTableCell-root:last-of-type": { pr: 3 },
          }}
        >
          <TableHead>
            <TableRow>
              {headCells.map((column) => (
                <TableCell
                  key={column.id}
                  align={column.align}
                  // sortDirection={orderBy === column.id ? order : false}
                  style={{minWidth: column.minWidth}}
                >
                  {column.label}
                  {/*/!*<TableSortLabel*!/*/}
                  {/*/!*  active={orderBy === column.id}*!/*/}
                  {/*/!*  direction={orderBy === column.id ? order : 'asc'}*!/*/}
                  {/*/!*  onClick={(event) => handleRequestSort(event, column.id)}*!/*/}
                  {/*/!*>*!/*/}
                  {/*  {column.label}*/}
                  {/*</TableSortLabel>*/}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
          <TableBody>
            {rows && rows.length > 0 ? (
              rows.map((row) => {
                // const isItemSelected = isSelected(row.trackingNo);
                // const labelId = `enhanced-table-checkbox-${index}`;
                       console.log("isOptional" + row.isOptional);
                return (
                  <TableRow
                    hover
                    role="checkbox"
                    sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                    // aria-checked={isItemSelected}
                    tabIndex={-1}
                    key={row.trackingNo}
                    // selected={isItemSelected}
                  >
                    <TableCell component="th"  scope="row" align="left">
                        {row.holidayName}
                    </TableCell>
                    <TableCell align="left">
                      {row.isOptional ? (
                        <span style={{ color: 'red' }}>Optional</span>
                      ) : (
                        <span style={{ color: 'green' }}>Mandatory</span>
                      )}
                    </TableCell>

                    <TableCell align="left">{row.holidayDate}</TableCell>

                    <TableCell align="left">{row.holidayDay}</TableCell>
                  </TableRow>
                );
              })
            ) : (
              <TableRow>
                <TableCell colSpan={5} align="center">
                  No holiday available
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}
