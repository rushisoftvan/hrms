import React from 'react';
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow } from '@mui/material';
// Ensure this component is correctly handling sorting
// import OrderStatus from "./OrderStatus"; // Ensure this component is correctly handling status

export default function CustomTable({ rows , headCells, renderHolidayRow , name , totalElement , page, handleChangePage , rowsPerPage  }) {

  console.log('props' +  JSON.stringify(rows));
  // const [order, setOrder] = useState("asc");
  // const [orderBy, setOrderBy] = useState("trackingNo");
  // const [selected, setSelected] = useState([]);

  // const handleRequestSort = (event, property) => {
  //   const isAsc = orderBy === property && order === "asc";
  //   setOrder(isAsc ? "desc" : "asc");
  //   setOrderBy(property);
  // };



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
              rows.map((row, index) => renderHolidayRow(row, index)) // ✅ Using renderHolidayRow here
            ) : (
              <TableRow>
                <TableCell colSpan={headCells.length} align="center">
                  No {name} available
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        // rowsPerPageOptions={[5, 10, 25]}
        component="div"
        count={totalElement}
       rowsPerPage={rowsPerPage}
        page={page}
       onPageChange={handleChangePage}
      />
    </Box>
  );
}
