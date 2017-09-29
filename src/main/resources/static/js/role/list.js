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
        role: {},
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
        add: function() {
            // 清空role
            this.role = {}
            role.addDialogVisible = true;
        },
        save: function () {
            var url = contentPath + rolePath + '/save';
            var data = JSON.stringify(this.role);
            console.log(data)
            $.ajax({
                url: url,
                data: data,
                type: 'POST',
                contentType: 'application/json;charset=utf-8',
                success: function(data) {
                    // console.log('success: ' + data);
                    // 关闭窗口
                    role.addDialogVisible = false;
                    pullData();
                },
                error: function(e) {
                    console.log('error: ' + e);
                }
            });

        },
        edit: function (id) {
            console.log('id = ' + id);
            var url = contentPath + rolePath + '/' + id;
            $.ajax({
                url: url,
                success: function (data) {
                    console.log(JSON.stringify(data));
                    role.role = data.data;
                    // 打开编辑窗口
                    role.editDialogVisible = true;
                    // console.log('role.editDialogVisible = ' + role.editDialogVisible);
                },
                error: function (e) {
                    console.log(e);
                }
            })

        },
        update: function () {
            var url = contentPath + rolePath + '/update';
            var data = JSON.stringify(this.role);
            console.log(data);
            $.ajax({
                url: url,
                data: data,
                type: 'POST',
                contentType: 'application/json;charset=utf-8',
                success: function(data) {
                    console.log('success: ' + data);
                    // 关闭窗口
                    role.editDialogVisible = false;
                    pullData();
                },
                error: function(e) {
                    console.log('error: ' + e);
                }
            });
        },
        doDelete: function(id) {
            console.log('delete id = ' + id);
            // 删除前确认
            this.$confirm('确认要删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function() {
                var url = contentPath + rolePath + '/delete/' + id;
                $.ajax({
                    url: url,
                    type: 'DELETE',
                    contentType: 'application/json;charset=utf-8',
                    dataType: 'json',
                    success: function(data) {
                        role.$notify({
                            title: '成功',
                            message: '删除成功',
                            type: 'success'
                        });
                        pullData();
                    },
                    error: function(e) {
                        console.log('error: ' + e);
                    }
                });
            }).catch(function() {
                return;
            });
        }
    }
});

function  pullData() {
    var url = contentPath + rolePath + '/list';
    // $.ajax({
    //     url: url,
    //     type: 'GET',
    //     success: function(data) {
    //         console.log('success: ' + JSON.stringify(data));
    //         role.roles = data.data;
    //     },
    //     error: function(e) {
    //         console.log('error: ' + e);
    //     }
    // });
    ajax(url, '', 'GET', {}).then(function(data) {
        console.log('success: ' + JSON.stringify(data));
        role.roles = data.data;
    }).catch(function(e) {
        console.log('error: ' + JSON.stringify(e));
    })
}

