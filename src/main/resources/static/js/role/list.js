var role = new Vue({
    el: '#role',
    data: {
        roles: [],
        multipleSelection: [],
        currentPage4: 4,
        input: '',
        value: '',
        addDialogVisible: false,
        editDialogVisible: false,
        role: {
            role: '',
        },
        formLabelWidth: '50px',
    },
    mounted: function() {
        // 拉取数据
        pullData();
    },
    methods: {
        handleOpen: function (key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose: function (key, keyPath) {
            console.log(key, keyPath);
        },
        toggleSelection: function (rows) {
            if (rows) {
                for (var i = 0; i < rows.length; i++) {
                    this.$refs.table.toggleRowSelection(rows[i]);
                }
            } else {
                this.$refs.table.clearSelection();
            }
        },
        handleSelectionChange: function (val) {
            this.multipleSelection = val;
        },
        handleSizeChange: function (val) {
            console.log("每页 ${val} 条");
        },
        handleCurrentChange: function (val) {
            console.log("当前页: ${val}");
        },
        closeDialog: function(visibleFlag) {
            var _this = this;
            this.$confirm('您还没有保存，是否退出?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function() {
                _this._data[visibleFlag] = false;
            }).catch(function() {

            });
        },
        save: function () {
            var url = contentPath + '/role/save';
            var data = {
                role : this.role.role
            };
            $.ajax({
                url: url,
                data: data,
                type: 'POST',
                success: function(data) {
                    console.log('success: ' + data);
                    role.addDialogVisible = true;
                    pullData();
                },
                error: function(e) {
                    console.log('error: ' + e);
                }
            });
        },
        edit: function (id) {
            console.log('id = ' + id);
            var url = contentPath + '/role/detail';
            $.ajax({
                url: url,
                data: {
                    id: id
                },
                success: function (data) {
                    console.log(JSON.stringify(data));
                    role.role = data;
                    role.editDialogVisible = true;
                    console.log('role.editDialogVisible = ' + role.editDialogVisible);
                },
                error: function (e) {
                    console.log(e);
                }
            })
        }

    }
});

function  pullData() {
    var url = contentPath + '/role/list';
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            console.log('success: ' + JSON.stringify(data));
            role.roles = data;
        },
        error: function(e) {
            console.log('error: ' + e);
        }
    });
}

