function viewDepartmentPage() {
    $(".main").load("./department/departmentPage.html", function () {
        setupSearchEvent();
        setupFilter();
        buildDepartmentTable();
    });
}

function buildDepartmentTable() {
    $('#department-table tbody').empty();
    getListDepartments();
}

var departments = [];

// paging
var currentPage = 1;
var size = 5;

// sorting
var sortField = "id";
var isAsc = false;

// get List
function getListDepartments() {
    var url = "http://localhost:8080/api/v1/departments";

    // paging
    url += '?page=' + currentPage + '&size=' + size;

    // sorting
    url += "&sort=" + sortField + "," + (isAsc ? "asc" : "desc");

    // search
    var search = document.getElementById("search-department-input").value;
    if (search) {
        url += "&search=" + search;
    }

    // filter
    var type = document.getElementById("filter-type-select").value;
    if (type && type != "All Types") {
        url += "&type=" + type;
    }


    // call API from server
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json",
        dataType: 'json', // datatype return
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")));
        },
        success: function (data, textStatus, xhr) {
            // success
            departments = data.content;
            fillDepartmentToTable();
            fillDepartmentPaging(data.numberOfElements, data.totalPages);
            fillDepartmentSorting();
        },
        error(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });

}

function fillDepartmentToTable() {
    departments.forEach(function (item, index) {
        $('#department-table tbody').append(
            '<tr>' +
            '<td> ' +
            '<span class="department-checkbox"> ' +
            '<input id="checkbox-' + index + '" type="checkbox" onClick="onChangeDepartmentCheckboxItem()"/>' +
            '<label></label>' +
            '</span>' +
            '</td>' +
            '<td>' + item.name + '</td>' +
            '<td>' + item.totalMember + '</td>' +
            '<td>' + item.type + '</td>' +
            '<td>' + item.createdDate + '</td>' +

            '<td class="td-actions"> ' +
            '<a href="#" data-toggle="tooltip" title="Edit" onclick="showUpdateDepartmentModal(' + item.id + ')"><i class="fa-solid fa-pencil"></i></a>' +
            '<a href="#" data-toggle="tooltip" title="Devare" onclick="showDeleteSingleDepartmentModal(' + item.id + ', \'' + item.name + '\')"><i class="fa-regular fa-trash-can"></i></a>' +
            '</td>' +
            '</tr>'
        );
    });
}

// paging
function fillDepartmentPaging(currentSize, totalPages) {
    // prev
    if (currentPage > 1) {
        document.getElementById("department-previousPage-btn").disabled = false;
    } else {
        document.getElementById("department-previousPage-btn").disabled = true;
    }

    // next
    if (currentPage < totalPages) {
        document.getElementById("department-nextPage-btn").disabled = false;
    } else {
        document.getElementById("department-nextPage-btn").disabled = true;
    }

    // text
    document.getElementById("department-page-info").innerHTML = currentSize + (currentSize > 1 ? " records " : " record ") + currentPage + " of " + totalPages;
}

function prevDepartmentPage() {
    changeDepartmentPage(currentPage - 1);
}

function nextDepartmentPage() {
    changeDepartmentPage(currentPage + 1);
}

function changeDepartmentPage(page) {
    currentPage = page;
    buildDepartmentTable();
}

// Sorting
function fillDepartmentSorting() {
    var sortTypeClazz = isAsc ? "fa-sort-up" : "fa-sort-down";
    var defaultSortType = "fa-sort";

    switch (sortField) {
        case 'name':
            changeIconSort("name-sort", sortTypeClazz);
            changeIconSort("createdDate-sort", defaultSortType);
            break;
        case 'createdDate':
            changeIconSort("name-sort", defaultSortType);
            changeIconSort("createdDate-sort", sortTypeClazz);
            break;

        // sort by id
        default:
            changeIconSort("name-sort", defaultSortType);
            changeIconSort("createdDate-sort", defaultSortType);
            break;
    }
}

function changeIconSort(id, sortTypeClazz) {
    document.getElementById(id).classList.remove("fa-sort", "fa-sort-up", "fa-sort-down");
    document.getElementById(id).classList.add(sortTypeClazz);
}

function changeDepartmentSort(field) {
    if (field == sortField) {
        isAsc = !isAsc;
    } else {
        sortField = field;
        isAsc = true;
    }
    buildDepartmentTable();
}

// search

function setupSearchEvent() {
    $("#search-department-input").on("keyup", function (event) {
        // enter key code = 13
        if (event.keyCode === 13) {
            buildDepartmentTable();
        }
    });
}

// filter
function filterDepartment() {
    buildDepartmentTable();
}

function setupFilter() {
    setupType();
    setupDepartmentFilter();
}

function setupType() {
    $("#filter-type-select").select2({
        placeholder: "Select a type"
    });
}

//chua lam
function setupDateFilter() {
    
}

// Refresh Table
function refreshDepartmentTable() {
    // refresh paging
    currentPage = 1;
    size = 5;

    // refresh sorting
    sortField = "id";
    isAsc = false;

    // refresh search
    document.getElementById("search-department-input").value = "";

    // refresh filter
    $("#filter-department-select").empty();
    $('#filter-type-select').val('').trigger('change');

    // Get API
    buildDepartmentTable();
}

function openDepartmentModal() {
    $('#addAndUpdateDepartmentModal').modal('show');
}

function hideDepartmentModal() {
    $('#addAndUpdateDepartmentModal').modal('hide');
}

// open create modal 
function openAddDepartmentModal() {
    openDepartmentModal();
    resetAddDepartmentForm();
}

function resetAddDepartmentForm() {
    // set title
    document.getElementById("addAndUpdateDepartment-modal-title").innerHTML = "Create New Department";

    // Reset all input value
    document.getElementById("modal-name").value = "";
    document.getElementById("modal-type-select").value = "PickAType";
    

    // type
    setupTypeSelectionInForm();


    // Reset all error message
    resetDepartmentModalErrMessage();
}

function setupTypeSelectionInForm() {
    $("#modal-type-select").select2({
        placeholder: "Select a type"
    });
}


function resetDepartmentModalErrMessage() {
    hideFieldErrorMessage("modal-input-errMess-name", "modal-name");
}

// save
function saveDepartment() {
    var id = document.getElementById("department-id").value;
    if (!id) {
        addDepartment();
    } else {
        updateDepartment();
    }
}

var error_message_name = "Department name must not be null";
var error_message_type = "You must choose type!";
;

function addDepartment() {
    var name = document.getElementById("modal-name").value;
    var type = document.getElementById("modal-type-select").value;
 

    // validate
    // var validname = isValidName(name);
    // var validType = isValidType(type);


    // format
    // if (!validname || !validType) {
    //     return;
    // }

    // check name unique
    $.ajax({
        url: "http://localhost:8080/api/v1/departments/name/" + name + "/exists",
        type: 'GET',
        contentType: "application/json",
        dataType: 'json', // datatype return
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")));
        },
        success: function (data, textStatus, xhr) {
            // error
            if (data == "error") {
                // TODO
                alert("Error when loading data");
                return;
            }

            if (data) {
                // show error message
                showFieldErrorMessage("modal-input-errMess-name", "modal-name", error_message_name_exists);
                return;
            } else {
                createDepartmentViaAPI(name, type);
            }
        },
        error(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function createDepartmentViaAPI(name, type) {
    // call api create department
    var newDepartment = {
        "name": name,
        "type": type,
    }

    $.ajax({
        url: 'http://localhost:8080/api/v1/departments',
        type: 'POST',
        data: JSON.stringify(newDepartment), // body
        contentType: "application/json", // type of body (json, xml, text)
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")));
        },
        success: function (data, textStatus, xhr) {
            // success
            hideDepartmentModal();
            showSuccessSnackBar("Success! New department created!");
            buildDepartmentTable();
        },
        error(jqXHR, textStatus, errorThrown) {
            alert("Error when loading data");
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

// function isValidName(name) {

//     if (!name) {
//         // show error message
//         showFieldErrorMessage("modal-input-errMess-name", "modal-name", error_message_name);
//         return false;
//     }

//     // validate format
//     var regex = new RegExp('^(?=.*[a-z])[a-zA-Z0-9_.-]{6,50}$');
//     if (!regex.test(name)) {
//         showFieldErrorMessage("modal-input-errMess-name", "modal-name", error_message_name);
//         return false;
//     };

//     hideFieldErrorMessage("modal-input-errMess-name", "modal-name");
//     return true;
// }


// function isValidType(type) {
//     if (!type) {
//         // show error message
//         showFieldErrorMessage("modal-input-errMess-type", "modal-type-select", error_message_type);
//         return false;
//     }

//     hideFieldErrorMessage("modal-input-errMess-type", "modal-type-select");
//     return true;
// }

function showFieldErrorMessage(messageId, inputId, message) {
    document.getElementById(messageId).innerHTML = message;
    document.getElementById(messageId).style.display = "block";
    document.getElementById(inputId).style.border = "1px solid red";
}

function hideFieldErrorMessage(messageId, inputId) {
    document.getElementById(messageId).style.display = "none";
    document.getElementById(inputId).style.border = "1px solid #ccc";
}

// delete single department
function showDeleteSingleDepartmentModal(departmentId, name) {
    $('#deleteSingleDepartmentModal').modal('show');
    document.getElementById('delete-single-department-confirm-mess').innerHTML = 'This action can not be undone. Delete <span style="color:red;">' + name + '</span>?';
    document.getElementById('delete-single-department-btn').onclick = function () { deleteSingleDepartment(departmentId) };
}

function deleteSingleDepartment(departmentId) {
    $.ajax({
        url: 'http://localhost:8080/api/v1/departments/' + departmentId,
        type: 'DELETE',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")));
        },
        success: function (result) {
            // error
            if (result == undefined || result == null) {
                alert("Error when loading data");
                return;
            }

            // success
            showSuccessSnackBar("Success! Department deleted.");
            $('#deleteSingleDepartmentModal').modal('hide');
            buildDepartmentTable();
        }
    });
}

// delete multiple department
function onChangeDepartmentCheckboxAll() {
    var i = 0;
    while (true) {
        var checkboxItem = document.getElementById("checkbox-" + i);
        if (checkboxItem !== undefined && checkboxItem !== null) {
            checkboxItem.checked = document.getElementById("checkbox-all").checked
            // if (document.getElementById("checkbox-all").checked) {
            //     checkboxItem.checked = true;
            // } else {
            //     checkboxItem.checked = false;
            // }
            i++;
        } else {
            break;
        }
    }
}

function onChangeDepartmentCheckboxItem() {
    var i = 0;
    while (true) {
        var checkboxItem = document.getElementById("checkbox-" + i);
        if (checkboxItem !== undefined && checkboxItem !== null) {
            if (!checkboxItem.checked) {
                document.getElementById("checkbox-all").checked = false;
                return;
            }
            i++;
        } else {
            break;
        }
    }
    document.getElementById("checkbox-all").checked = true;
}

function showDeleteMultipleDepartmentsModal() {
    $('#deleteMultipleDepartmentsModal').modal('show');

    // get checked
    var ids = [];
    var names = [];
    var i = 0;
    while (true) {
        var checkboxItem = document.getElementById("checkbox-" + i);
        if (checkboxItem !== undefined && checkboxItem !== null) {
            if (checkboxItem.checked) {
                ids.push(departments[i].id);
                names.push(departments[i].fullName);
            }
            i++;
        } else {
            break;
        }
    }

    if (!ids || ids.length == 0) {
        document.getElementById('delete-departments-confirm-mess').innerHTML = 'Choose at least one department to delete!';
        document.getElementById('delete-multiple-departments-btn').style.display = 'none';
    } else {
        document.getElementById('delete-departments-confirm-mess').innerHTML = 'This action can not be undone. Delete <span id="department-name-delete-message"></span>?';
        document.getElementById('department-name-delete-message').innerHTML += '<span style="color: red;">' + names.join(", ") + '</span> (<span style="color: red;">' + names.length + '</span> ' + (names.length == 1 ? 'department' : 'departments') + ')';
        document.getElementById('delete-multiple-departments-btn').style.display = 'inline-block';
        document.getElementById('delete-multiple-departments-btn').onclick = function () { deleteMultipleDepartments(ids) };
    }
}

function deleteMultipleDepartments(departmentIds) {
    $.ajax({
        url: 'http://localhost:8080/api/v1/departments?ids=' + departmentIds.toString(),
        type: 'DELETE',
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")));
        },
        success: function (result) {
            // error
            if (result == undefined || result == null) {
                alert("Error when loading data");
                return;
            }

            // success
            showSuccessSnackBar("Success! Department deleted.");
            $('#deleteMultipleDepartmentsModal').modal('hide');
            buildDepartmentTable();
        }
    });
}

function resetFormUpdateDepartment() {
    // set title
    document.getElementById("addAndUpdateDepartment-modal-title").innerHTML = "Update Department";

    //display data of this department can be block
    // document.getElementById("modal-type-select").style.display = "block";
    // document.getElementById("modal-department-select").style.display = "block";

    // Reset all error message
    resetDepartmentModalErrMessage();
}

var oldname;

function showUpdateDepartmentModal(id) {
    $.ajax({
        url: "http://localhost:8080/api/v1/departments/" + id,
        type: 'GET',
        contentType: "application/json",
        dataType: 'json', // datatype return
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")));
        },
        success: function (data, textStatus, xhr) {
            // success
            openDepartmentModal();
            resetFormUpdateDepartment();

            oldname = data.name;

            // fill data
            document.getElementById("modal-name").value = data.name;
            document.getElementById("modal-type-select").value = data.type;
            
        },
        error(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }
    });
}

function updateDepartment() {
    
}