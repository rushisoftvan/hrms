import React, { useEffect, useState } from 'react';
import {
  Button,
  Modal,
  Box,
  Typography,
  TextField,
  MenuItem,
  Grid
} from '@mui/material';
import { applyLeave, fetchLeaveType } from '../../../api/leave.api';

// const leaveTypes = [
//   { id: 1, label: 'Sick Leave' },
//   { id: 2, label: 'Casual Leave' },
//   { id: 3, label: 'Earned Leave' },
//   { id: 4, label: 'Maternity Leave' },
// ];

const AddLeaveModal = ({ openModal, handleClose }) => {
  const [formData, setFormData] = useState({
    leaveTypeId: '',
    startDate: '',
    endDate: '',
    leaveReason: ''
  });

  const [leaveTypes, setLeaveTypes] = useState([]);

  useEffect(() => {
    if (openModal) {
      const getLeaveTypes = async () => {
        try {
          const response = await fetchLeaveType();
          console.log(response.data.data.dropDownLeaveType
          );
          setLeaveTypes(response.data.data.dropDownLeaveType);
          console.log(leaveTypes)// Adjust depending on your API response shape
        } catch (error) {
          console.error('Failed to fetch leave types:', error);
        }
      };

      getLeaveTypes();
    }
  }, [openModal]);




  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'leaveTypeId' ? Number(value) : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const payload = {
      ...formData,
      startDate: new Date(formData.startDate).toISOString(),
      endDate: new Date(formData.endDate).toISOString(),
    };

    console.log('Leave submitted:', payload);

    try {
      const response = await applyLeave(payload);
      console.log('Leave submitted successfully:', response);
      toastRef.current.show({
        severity: 'success',
        summary: 'Leave Submitted',
        detail: 'Your leave has been successfully submitted.',
        life: 3000, // Automatically close after 3 seconds
      });
      // Optionally show success toast or alert here
      handleClose(); // Close modal after success
    } catch (error) {
      toastRef.current.show({
        severity: 'error',
        summary: 'Leave Application Failed',
        detail: 'There was an error submitting your leave request.',
        life: 3000, // Automatically close after 3 seconds
      });
      console.error('Error applying for leave:', error);

      // Optionally show error message to user
    }

    handleClose();
  };

  return (
    <Modal
      open={openModal}
      onClose={handleClose}
      aria-labelledby="add-leave-modal-title"
    >
      <Box
        sx={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: 500,
          bgcolor: 'background.paper',
          boxShadow: 24,
          p: 4,
          borderRadius: 2,
        }}
      >
        <Typography id="add-leave-modal-title" variant="h6" gutterBottom>
          Add Leave
        </Typography>

        <form onSubmit={handleSubmit}>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <TextField
                select
                fullWidth
                label="Leave Type"
                name="leaveTypeId"
                value={formData.leaveTypeId}
                onChange={handleChange}
                required
              >
                {leaveTypes.map((type) => (
                  <MenuItem key={type.value} value={type.key}>
                    {type.value}
                  </MenuItem>
                ))}
              </TextField>
            </Grid>

            <Grid item xs={6}>
              <TextField
                fullWidth
                type="date"
                label="Start Date"
                name="startDate"
                value={formData.startDate}
                onChange={handleChange}
                InputLabelProps={{ shrink: true }}
                required
              />
            </Grid>

            <Grid item xs={6}>
              <TextField
                fullWidth
                type="date"
                label="End Date"
                name="endDate"
                value={formData.endDate}
                onChange={handleChange}
                InputLabelProps={{ shrink: true }}
                required
              />
            </Grid>

            <Grid item xs={12}>
              <TextField
                fullWidth
                multiline
                rows={3}
                label="Leave Reason"
                name="leaveReason"
                value={formData.leaveReason}
                onChange={handleChange}
                required
              />
            </Grid>

            <Grid item xs={12} display="flex" justifyContent="flex-end">
              <Button onClick={handleClose} color="secondary">
                Cancel
              </Button>
              <Button type="submit" variant="contained" sx={{ ml: 2 }}>
                Submit
              </Button>
            </Grid>
          </Grid>
        </form>
      </Box>
    </Modal>
  );
};

export default AddLeaveModal;
