import { useEffect, useState } from 'react';

import { fetchHolidayList } from '../../../api/holiday.api';
import CustomTable from '../../../custom/CustomTable';
import { FormControl, FormControlLabel, Radio, RadioGroup, TextField } from '@mui/material';
import { debounce } from 'lodash';

const HolidayList = () => {
  const [holidayData, setHolidayData] = useState([]);
  const [holidayStatus, setHolidayStatus] = useState('All');
  const [searchQuery, setSearchQuery] = useState('');
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetchHolidayList({
          holidayStatus: null,
          searchText: null
        }); // Await the API call
        setHolidayData(response.data.data);
        console.log(response.data.data);
        // Assuming response contains the holiday list
        // console.log('e' + JSON.stringify(response.data.data));
        //  console.log(JSON.stringify(response.data.data));
      } catch (err) {
        console.log(err);
      }
    };
    fetchData();
  }, []);

  // Function to call API for "Upcoming"
  const onUpcoming = async () => {
    try {
      const response = await fetchHolidayList({
        holidayStatus: 'UPC', // Pass status for upcoming holidays
        searchText: null
      });
      setHolidayData(response.data.data); // Update the holiday data state
      console.log('Upcoming Holidays:', response.data.data);
    } catch (err) {
      console.log('Error fetching upcoming holidays:', err);
    }
  };

  // Function to call API for "Has Gone"
  const onHasGone = async () => {
    try {
      const response = await fetchHolidayList({
        holidayStatus: 'HG', // Pass status for holidays that have gone
        searchText: null
      });
      setHolidayData(response.data.data); // Update the holiday data state
      console.log('Holidays that have gone:', response.data.data);
    } catch (err) {
      console.log('Error fetching holidays that have gone:', err);
    }
  };

  const onHandleSerach = debounce(async (value) => {
    try {
      const response = await fetchHolidayList({
        holidayStatus: null, // Pass status for holidays that have gone
        searchText: value.trim() // Use search query from input
      });
      setHolidayData(response.data.data); // Update the holiday data state
      console.log('Holidays that have gone:', response.data.data);
      setTimeout(() => {
        setSearchQuery("");
      }, 1000);
    } catch (err) {
      console.log('Error fetching holidays that have gone:', err);
    }
  }, 500);

  // when status all

  const onAllHandleRadioButton = async () =>{

    try {
      const response = await fetchHolidayList({
        holidayStatus: null, // Pass status for holidays that have gone
        searchText: null
      });
      setHolidayData(response.data.data); // Update the holiday data state
      console.log('Holidays that have gone:', response.data.data);
    } catch (err) {
      console.log('Error fetching holidays that have gone:', err);
    }
  }

  // Handle radio button change
  const handleRadioChange = (event) => {
    const value = event.target.value;
    setHolidayStatus(value);

    if (value === 'UpComping') {
      onUpcoming(); // Fetch upcoming holidays when the "Upcoming" radio button is selected
    } else if (value === 'hasGone') {
      onHasGone(); // Fetch holidays that have gone when the "HasGone" radio button is selected
    }
    else if(value === 'All'){
      onAllHandleRadioButton();
    }
  };

  return (
    <div>
      <h1>Holiday List</h1>
      <FormControl>
        <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
          <TextField
            id="search-bar"
            className="text"
            // onInput={(e) => setSearchQuery(e.target.value)}
            label="Enter holiday name"
            variant="outlined"
            placeholder="Search..."
            size="small"
            value={searchQuery}
            onChange={(e) => {
              setSearchQuery(e.target.value);
              onHandleSerach(e.target.value);
            }}
          />
          <RadioGroup
            row
            aria-labelledby="demo-row-radio-buttons-group-label"
            name="row-radio-buttons-group"
            value={holidayStatus}
            onChange={handleRadioChange}
          >
            <FormControlLabel value="UpComping" control={<Radio />} label="Upcoming" />
            <FormControlLabel value="hasGone" control={<Radio />} label="Past Holidays" />
            <FormControlLabel value="All" control={<Radio />} label="All"/>

          </RadioGroup>
        </div>
      </FormControl>
      <CustomTable rows={holidayData} />
    </div>
  );
};
export default HolidayList;
