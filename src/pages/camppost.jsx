// import { Grid } from "@material-ui/core";
// import useStyles from "../styles/components/post";
// import useAxios from "../hooks/axios";
// import Camp from "../components/camp/camp";
// import { DateRange } from "@material-ui/icons";
// const Camppost = () => {
//     const classes = useStyles();

//   const [camps, error, waiting] = useAxios(`/camps/`);
//     return (
//         <Grid container spacing={2}>
//       {
//         camps &&
//         camps.map((card, idx) => (
//           <Grid item xs={12} sm={6} md={4} className={classes.postContainer} key={idx}>
//             <Camp {...card} />
//           </Grid>
//         ))
//       }
//     </Grid>
//       );
// }
 
// export default Camppost ;
import {
  Backdrop,
  CircularProgress,
  Paper,
  Table,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TableBody,
  Typography,
  Divider,
  Fab,
  IconButton,
  Snackbar,
} from "@material-ui/core";
import { Alert } from "@material-ui/lab";
import { Add, Delete } from "@material-ui/icons";
import { useAxios } from "../hooks/";
import useStyles from "../styles/pages/bloodbank";
import { useSelector } from "react-redux";
import BloodBankForm from "../components/blood-bank-form";
import { useState } from "react";
import axios from "../config/axios.config";
import CampForm from "../components/camp-form";

const CampPostTable = (props) => {
  const classes = useStyles();
  const userInfo = useSelector((store) => store.user);
  const [camps, error, waiting] = useAxios("/camp/");
  const [open, handleOpen] = useState(false);
  const [response, handleResponse] = useState({
    loading: false,
    open: false,
  });

  const handleDelete = (id) => {
    handleResponse((res) => ({ ...res, loading: true }));
    axios
      .delete(`/camp/delete/${id}`)
      .then((res) => {
        handleResponse((res) => ({
          ...res,
          severity: "success",
          message: "Blood Donation Camp Deleted Successfully",
        }));
      })
      .catch((err) => {
        handleResponse((res) => ({
          ...res,
          severity: "error",
          message: "Failed To Delete Camp Details",
        }));
      })
      .finally(() => {
        handleResponse((res) => ({ ...res, open: true, loading: false }));
      });
  };

  return (
    <>
      <Backdrop className={classes.backdrop} open={waiting}>
        <CircularProgress color="inherit" />
      </Backdrop>

      <Snackbar
        open={response.open}
        autoHideDuration={6000}
        anchorOrigin={{ vertical: "top", horizontal: "right" }}
      >
        <Alert severity={response.severity}>{response.message}</Alert>
      </Snackbar>

      <CampForm open={open} handleClose={() => handleOpen(false)} />

      <TableContainer component={Paper} className={classes.container}>
        <Typography component="h1" className={classes.title}>
          Upcoming Donation Camps
          {(userInfo.authority === "ADMIN") && (
            <Fab
              variant="extended"
              color="primary"
              className={classes.add_btn}
              onClick={() => handleOpen(!false)}
            >
              <Add />
             Add Blood Donation Camp
            </Fab>
          )}
        </Typography>
        <Divider absolute />
        <Table className={classes.table} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>Reg. ID</TableCell>
              <TableCell align="right">Name</TableCell>
              <TableCell align="right">Date</TableCell>
              <TableCell align="right">Locality</TableCell>
              <TableCell align="right">City</TableCell>
              <TableCell align="right">District</TableCell>
              <TableCell align="right">Zipcode</TableCell>
              <TableCell align="right">Open At</TableCell>
              <TableCell align="right">Close At</TableCell>
              {(userInfo.authority === "ADMIN") && (
                <TableCell align="right">Delete</TableCell>
              )}
            </TableRow>
          </TableHead>
          <TableBody>
            {camps &&
              camps.map((row) => (
                <TableRow key={row.id}>
                  {/* <TableCell component="th" scope="row">
                    <Link to={`/camp/${row.id}`} component={RLink}>
                      {row.regID}
                    </Link>
                  </TableCell> */}
                  <TableCell align="right">{row.name}</TableCell>
                  <TableCell align="right">{row.campDate}</TableCell>
                  <TableCell align="right">{row.locality}</TableCell>
                  <TableCell align="right">{row.city}</TableCell>
                  <TableCell align="right">{row.district}</TableCell>
                  <TableCell align="right">{row.zipcode}</TableCell>
                  <TableCell align="right">{row.openAt}</TableCell>
                  <TableCell align="right">{row.closeAt}</TableCell>
                  {(userInfo.authority === "ADMIN"
        ) && (
                    <TableCell align="center">
                      <IconButton
                        aria-label="delete"
                        color="secondary"
                        onClick={() => handleDelete(row.id)}
                      >
                        <Delete />
                      </IconButton>
                    </TableCell>
                  )}
                </TableRow>
              ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
};

export default CampPostTable;
