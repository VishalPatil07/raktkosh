import {
    Container,
    Divider,
    Paper,
    Typography,
    Table,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TableBody,
    Backdrop,
    CircularProgress,
    IconButton,
    Fab,
    Snackbar,
} from "@material-ui/core";
import {
    TimerSharp,
    Email,
    Phone,
    VerifiedUser,
    LocationCity,
    Delete,
    Add,
} from "@material-ui/icons";
import { useEffect, useState } from "react";
import { useHistory, useParams, useLocation } from "react-router";
import { useAxios } from "../hooks";
import axios from "../config/axios.config";
import useStyles from "../styles/pages/bloodbankdetails";
import { useSelector } from "react-redux";

import BankRepositoryForm from "../components/blood-repository-form";
import { Alert } from "@material-ui/lab";
const Repository = (props) => {
    const classes = useStyles();
    const repo = useAxios(`/bloodrepo/${userInfo.id}`)
   // const [repo, handleRepo] = useState([]);
    const [open, handleOpen] = useState(false);
    const userInfo = useSelector(store => store.user);
 // const classes = useStyles();
  const [response, handleResponse] = useState({
    loading: false,
    open: false,
  });
const [id, setId] = useState();

    const handleDelete = (repoID) => {
        axios
          .delete(`/bloodrepo/${userInfo.id}`, { data: repoID })
          .then((res) => {
            handleResponse((res) => ({
              ...res,
              severity: "success",
              message: "Blood Repository Deleted Successfully",
            }));
           // history.push(location.pathname);
          })
          .catch((err) => {
            handleResponse((res) => ({
              ...res,
              severity: "error",
              message: "Failed To Delete Blood Repository",
            }));
          })
          .finally(() => {
            handleResponse((res) => ({ ...res, open: true, loading: false }));
          });
      };


    return (
        <Container>
            <TableContainer className={classes.container}>
                <Typography component="h1" className={classes.table_title}>
                    Repository
                </Typography>
                <Table className={classes.table} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="center">Blood Group</TableCell>
                            <TableCell align="center">Antigen</TableCell>
                            <TableCell align="center">Units Available</TableCell>

                            <TableCell align="center">Delete</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {repo.map((row, idx) => (
                            <TableRow key={idx}>
                                <TableCell align="center">{row.id.type}</TableCell>
                                <TableCell align="center">{row.id.antigen}</TableCell>
                                <TableCell align="center">{row.availability}</TableCell>

                              
  
                               
                                    <TableCell align="center">

                                        <IconButton aria-label="delete" color="secondary" onClick={() => handleDelete({
                                            type: row.id.type,
                                            antigen: row.id.antigen
                                        })}>
                                            <Delete />
                                        </IconButton>

                                    </TableCell>
                               
                               


                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Container>
    )
}
export default Repository;