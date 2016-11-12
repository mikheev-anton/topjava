var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: updateTableByData
    });
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type == 'display') {
                        return date.replace('T', ' ');
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            row.className = !data.exceed ? 'normal' : 'exceeded';
        },
        "initComplete": makeEditable
    });
    var startDate = $('#startDate');
    var endDate = $('#endDate');
    startDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        lang: 'ru',
        formatDate:'Y-m-d',
        onShow: function(st){
            this.setOptions({
                maxDate: endDate.val() ? endDate.val():false
            })
        }
    });
    endDate.datetimepicker({
        timepicker: false,
        format : 'Y-m-d',
        lang: 'ru',
        formatDate:'Y-m-d',
        onShow: function(st){
            this.setOptions({
                maxDate: startDate.val() ? startDate.val():false
            })
        }
    });
    $('.time-picker').datetimepicker({
        datepicker: false,
        format : 'H:i',
        lang:'ru'
    });
    $('#dateTime').datetimepicker({
        format : 'Y-m-d//TH:i',
        lang : 'ru'
    });
});