var menu = new Vue({
    el: '#menu',
    data: {
        // 添加对话框显示/隐藏标志
        addDialogVisible: false,
        // 编辑对话框显示/隐层标志
        updateDialogVisible: false,
        // 选择上级菜单对话框显示/隐层标志
        selectParentDialogVisible: false,
        formLabelWidth: '120px',
        columns: [
            {
                text: '名称',
                dataIndex: 'name'
            },
            {
                text: '路径',
                dataIndex: 'url'
            },
            {
                text: '类型',
                dataIndex: 'type'
            },
            {
                text: '权限',
                dataIndex: 'permission'
            }
        ],
        expandAll: false,
        // 菜单列表
        menus: [],
        // 菜单树列表
        menuTrees: [],
        // 当前编辑的菜单对象
        menu: {},
        // 树默认属性
        defaultProps: {
            children: 'children',
            label: 'label'
        },
        selectedParentId: '',// 选中的上级菜单id
        selectedParentName: '',// 选中的上级菜单名称
        // 选择上级菜单树列表
        parentTree: [],
    },
    mounted: function() {
        // console.log('mounted');
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
            // 清空menu
            this.menu = {};
            this.addDialogVisible = true;
        },
        doAdd: function () {
            var _this = this;
            var url = contentPath + menuPath + '/save';
            // var data = JSON.stringify(this.menu);
            // console.log(data)
            ajax(url, this.menu, 'POST', null).then(function(data) {
                // 关闭窗口
                _this.addDialogVisible = false;
                success("添加成功");
                pullData();
            });

        },
        goUpdate: function (id) {
            var _this = this;
            console.log('id = ' + id);
            var url = contentPath + menuPath + '/' + id;
            ajax(url, null, 'GET', null).then(function(data) {
                _this.menu = data.data;
                // 打开编辑窗口
                _this.updateDialogVisible = true;
            });

        },
        doUpdate: function () {
            var _this = this;
            var url = contentPath + menuPath + '/update';
            // var data = JSON.stringify(this.menu);
            // console.log(data);
            ajax(url, this.menu, 'POST', null).then(function(data) {
                console.log('success: ' + data);
                // 关闭窗口
                _this.updateDialogVisible = false;
                success("更新成功");
                pullData();
            });
        },
        doDelete: function(id) {
            var _this = this;
            console.log('delete id = ' + id);
            // 删除前确认
            this.$confirm('确认要删除?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function() {
                var url = contentPath + menuPath + '/delete/' + id;
                ajax(url, null, 'DELETE', null).then(function(data) {
                    success("删除成功");
                    pullData();
                });
            }).catch(function() {
                return;
            });
        },
        // 添加下级菜单
        goAddChildren: function(row) {
            // 清空
            this.menu = {};
            this.menu.parentId = row.id;
            this.menu.parentName = row.name;
            this.addDialogVisible = true;
        },
        /******************************* 树操作方法 *******************************************/
        // 是否显示行
        showTr: function(row, index) {
            var show = (row._parent ? (row._parent._expanded && row._parent._show) : true)
            row._show = show
            return show ? '' : 'display:none;'
        },
        // 展开/收起下级
        toggle: function(index) {
            console.log('toggle index = ' + index);
            var _this = this
            var item = _this.menuTrees[index];
            if (!item._expanded) {
                // 显示加载图标
                Vue.set(item, '_loading', true);
                var _this = this;
                setTimeout(function() {
                    // _this.appendChildren(index);
                    item._loading = false;
                    item._expanded = true;
                }, 1000);

            } else {
                item._expanded = false;
            }

//				console.log(item);

        },
        // 添加子节点
        appendChildren: function(index) {
            var d = [{
                id: 1 - 1,
                parentId: 1,
                name: '测试1-1',
                age: 18,
                sex: '男',
            }];
            var _this = this
            var record = _this.menuTrees[index];
            var c = treeToArray(d, record, false);
            console.log('c = ' + c);
            record.children = c;
            record._loading = false;

        },
        // 显示层级关系的空格和图标
        spaceIconShow: function(index) {
            if(index === 0) {
                return true;
            }
            return false;
        },
        // 是否显示展开/收起图标
        toggleIconShow: function(index, record) {
            if(index === 0 && record.children && record.children.length > 0) {
                return true;
            }
            return false;
        },
        /**************************** 选择上级菜单 *********************************************/
        // 打开选择上级菜单对话框
        selectParent: function(event) {
            this.parentTree = this.menus;
            this.selectParentDialogVisible = true;
            // 使当前元素失去焦点
            event.target.blur();
        },
        // 选择上级菜单
        selectMenu: function(data) {
            console.log(data);
            this.selectedParentId = data.id;
            this.selectedParentName = data.name;
        },
        // 取消选择
        cancelSelect: function() {
            this.selectedParentId = "";
            this.selectedParentName = "";
            // 隐藏选择上级菜单对话框
            this.selectParentDialogVisible = false;
        },
        // 确定选择，将选中的parentId和parentName赋值给menu
        confirmSelect: function() {
            // 不允许选择当前菜单作为上级菜单
            if (this.selectedParentId != '' && this.selectedParentId == this.menu.id) {
                this.$message({
                    type: 'error',
                    message: '请不要选择当前菜单作为上级菜单'
                });
                return;
            }
            this.menu.parentId = this.selectedParentId;
            this.menu.parentName = this.selectedParentName;
            // 隐藏选择上级菜单对话框
            this.selectParentDialogVisible = false;
        }
    }
});

function  pullData() {
    var _this = menu;
    var url = contentPath + menuPath + '/list';
    ajax(url, '', 'GET', {}).then(function(data) {
        console.log('success: ' + JSON.stringify(data));
        menu.menus = data.data;
        menu.menuTrees = treeToArray(data.data, null, menu.expandAll);

        // menu.loading = false;
    });
}

/**
 * 树形结构转数组
 * @param {Object} data 树形结构数据
 * @param {Object} parent 它爸
 * @param {Object} isExpand 是否展开
 */
function treeToArray(data, parent, isExpand) {
    var result = []
    // 遍历树形数据
    Array.from(data).forEach(function(item) {
        // console.log('tree to array item = ' + item);
        // 是否展开
        if(item._expanded === undefined) {
            Vue.set(item, '_expanded', isExpand);
        }
        // 如果不是根节点，设置它爸
        if(parent) {
            Vue.set(item, '_parent', parent);
        }
        // 将结果放入result
        result.push(item);
        // 存在子节点，递归转换
        if(item.children && item.children.length > 0) {
            var children = treeToArray(item.children, item, isExpand);
            result = result.concat(children);
        }
    })
    // console.log('result = ' + result);
    return result
}
