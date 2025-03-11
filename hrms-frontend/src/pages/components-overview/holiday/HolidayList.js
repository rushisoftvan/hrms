import { useEffect, useState } from 'react';

import { fetchHolidayList } from '../../../api/holiday.api';

const HolidayList = () => {
  const [holidayData, setHolidayData] = useState([]);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetchHolidayList({
          holidayStatus: null,
          searchText: null
        }); // Await the API call
        setHolidayData(response); // Assuming response contains the holiday list
        console.log(response);
        console.log(holidayData);
      } catch (err) {
       console.log(err);
      }
    };
    fetchData();
  }, []);


  return (
    <div>
      <h1>Holiday List</h1>
    </div>
  );
}
export default HolidayList;