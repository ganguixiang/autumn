var role = new Vue({
    el: '#role',
    data: {
        // 角色列表
        roles: [],
        // 多选数组
        multipleSelection: [],
        currentPage4: 4,
        input: '',
        value: '',
        // 添加窗口显示/隐藏标志
        addDialogVisible: false,
        // 修改窗口显示/隐藏标志
        updateDialogVisible: false,
        // 设置权限窗口显示/隐藏标志
        selectMenuDialogVisible: false,
        // 正在编辑的角色对象
        role: {},
        formLabelWidth: '80px',
        // loading: true,
        // 树形结构默认属性
        defaultProps: {
            children: 'children',
            label: 'label'
        },
        menuTrees:[], // 菜单列表
        selectedMenus:[], // 已选择菜单列表
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
        goAdd: function() {
            // 清空
            this.role = {}
            this.addDialogVisible = true;
        },
        doAdd: function () {
            var _this = this;
            var url = contentPath + rolePath + '/save';
            // var data = JSON.stringify(this.role);
            // console.log(data)
            ajax(url, this.role, 'POST', null).then(function(data) {
                // 关闭窗口
                _this.addDialogVisible = false;
                _this.$message({
                    message: '保存成功',
                    type: 'success'
                });
                pullData();
            });

        },
        goUpdate: function (id) {
            var _this = this;
            console.log('id = ' + id);
            var url = contentPath + rolePath + '/' + id;
            ajax(url, null, 'GET', null).then(function(data) {
                _this.role = data.data;
                // 打开编辑窗口
                _this.updateDialogVisible = true;
            });

        },
        doUpdate: function () {
            var _this = this;
            var url = contentPath + rolePath + '/update';
            // var data = JSON.stringify(this.role);
            // console.log(data);

            ajax(url, this.role, 'POST', null).then(function(data) {
                console.log('success: ' + data);
                // 关闭窗口
                _this.updateDialogVisible = false;
                _this.$message({
                    message: '保存成功',
                    type: 'success'
                });
                pullData();
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
                ajax(url, null, 'DELETE', null).then(function(data) {
                    role.$message({
                        message: '删除成功',
                        type: 'success'
                    });
                    pullData();
                });
            }).catch(function() {
                return;
            });
        },
        /************************ 选择权限 ********************************/
        goSelectMenu: function(id) {
            var _this = this;
            var url = contentPath + rolePath + '/' + id + '/' + 'go-select-menu';
            ajax(url, '', 'GET', {}).then(function(data) {
                console.log(data);
                _this.menuTrees = data.data.menuTrees;
                _this.selectedMenus = data.data.selectedMenus;
                _this.role = data.data.role;
                console.log(_this.selectMenuDialogVisible);
                _this.selectMenuDialogVisible = true;
                console.log(_this.selectMenuDialogVisible);
            });
        },
        // 取消选择
        cancelSelect: function() {
            // 隐藏选择上级菜单对话框
            this.selectMenuDialogVisible = false;
        },
        // 确定选择
        confirmSelect: function() {
            // 获取选中的菜单id
            var menuIds = this.$refs.menuTree.getCheckedKeys();
            var _this = this;
            var url = contentPath + rolePath + '/' + this.role.id + '/update-menu';
            ajax(url, menuIds, 'POST', {"traditional" : true}).then(function(data) {
                _this.$message({type:"success", message:"设置权限成功"});
                _this.selectMenuDialogVisible = false;
            });

        }
    }
});

function  pullData() {
    var url = contentPath + rolePath + '/list';
    ajax(url, '', 'GET', {}).then(function(data) {
        console.log('success: ' + JSON.stringify(data));
        role.roles = data.data;
        // role.loading = false;
    });
}

