define(["myFramework/MyExports","text"], function() {
	function _Remote() {
		function _StoreOperation(name, Model) {
			this.validators={};
			this.name = name;
			this.Model = Model;
		}
		;
		_StoreOperation.prototype.new = function(data) {
			//debugger;
			var _model=new this.Model(data);
			
			return _model;
		}

		// 通过ID是否为空来控制create or update
		_StoreOperation.prototype.save = function(data, success, fail) {
			
			var _model = new this.Model(data);
			var _fail = fail;
			if (_fail == undefined) {
				_fail = function(message) {
					MF.Mask.toast(message);
				};
			}
			if (success == undefined)
				return _model.save();
			else
				return _model.save(success, _fail);
		};
		_StoreOperation.prototype.findOne = function(id, success, fail) {
			var _fail = fail;
			if (_fail == undefined) {
				_fail = function(message) {
					MF.Mask.toast(message);
				};
			}
			if (success == undefined)
				return this.Model.findOne({
					id : id
				});
			else
				return this.Model.findOne({
					id : id
				}, success, _fail);
		};
		_StoreOperation.prototype.findAll = function(params, success, fail) {
			var _fail = fail;
			if (_fail == undefined) {
				_fail = function(message) {
					MF.Mask.toast(message);
				};
			}
			if (success == undefined)
				return this.Model.findAll(params);
			else
				return this.Model.findAll(params, success, _fail);
		};

		function _createCanModel(name,options) {
			var _options = {
				parseModel : function(data) {
					if (data.status) {
						return data.data;
					} else {
						throw data.message;
					}
				},
				parseModels : function(data) {
					if (data.status) {
						var result = [];
						can.each(data.data, function(value, index) {
							result.push({
								status : true,
								data : value
							});
						})
						return result;
					} else {
						throw data.message;
					}
				}
				
			};
			if (options.create){
				
				_options.create=function( attrs ){
					var urls=options.create.split(" ");
					var _type="POST";
					var _url="";
					if (urls.length>1){
						_type=urls[0];
						_url=urls[1];
					}else
						_url=options.create;
					return can.ajax({
						url : _url,
						type : _type,
						dataType : "json",
						contentType : "application/json",
						data:JSON.stringify(sign.signData(attrs))
					});
				}
			};
			if (options.update){
				_options.update=function(id, attrs){
					var urls=options.update.split(" ");
					var _type="POST";
					var _url="";
					if (urls.length>1){
						_type=urls[0];
						_url=urls[1];
					}else
						_url=options.update;
					_url=_url.replace("{id}",id);
					return can.ajax({
						url : _url,
						type : _type,
						dataType : "json",
						contentType : "application/json",
						data:JSON.stringify(sign.signData(attrs))
					});
				}
			};
			if (options.destroy){
				_options.destroy=function(id){
					var urls=options.destroy.split(" ");
					var _type="POST";
					var _url="";
					if (urls.length>1){
						_type=urls[0];
						_url=urls[1];
					}else
						_url=options.destroy;
					_url=_url.replace("{id}",id);
					return can.ajax({
						url : _url,
						type : _type,
						contentType : "application/json",
						dataType : "json",
						data:sign.signData()
					});
				}
			};
			if (options.findOne){
				_options.findOne=function(params){
					var urls=options.findOne.split(" ");
					var _type="GET";
					var _url="";
					if (urls.length>1){
						_type=urls[0];
						_url=urls[1];
					}else
						_url=options.findOne;
					_url=_url.replace("{id}",params.id);
					return can.ajax({
						url : _url,
						type : _type,
						contentType : "application/json",
						dataType : "json",
						data:sign.signData()
					});
				}
			};
			if (options.findAll){
				_options.findAll=function(params){
					var urls=options.findAll.split(" ");
					var _type="POST";
					var _url="";
					if (urls.length>1){
						_type=urls[0];
						_url=urls[1];
					}else
						_url=options.findAll;
					return can.ajax({
						url : _url,
						type : _type,
						contentType : "application/json",
						dataType : "json",
						data:JSON.stringify(sign.signData(params))
					});
				}
			};
			if (true){
				_options.init=function(){
					for (var indx in options.validators){
						//添加自定义的校验
						var validator = options.validators[indx];
						if(can.isFunction(validator.proc)){
							this.validate(validator.attr,validator.proc);
						}else{
							var params=[];
							params.push(validator.attr);
							params = params.concat(validator.opt);
							this[validator.proc].apply(this,params);
						}
					}
				}
			}
			
			return can.Model(name,_options,{});
		}

		// 定义数据集
		this.define = function(name, options) {
			// 生成模型实例
			
			this[name] = new _StoreOperation(name,_createCanModel(name,options));
		};
		// ajax
		this.get = function(url, object, success, fail) {
			var _url = url + "?" + can.param(object);
			var _defrend = can.ajax({
				url : url,
				type : "GET",
				dataType : "json",
				data:sign.signData()
			});
			var _fail = fail;
			if (_fail == undefined) {
				_fail = function(message) {
					MF.Mask.toast(message);
				};
			}
			if (success == undefined)
				return _defrend;
			else
				return _defrend.then(success, _fail);
		};
		this.post = function(url, object, success, fail) {
			var _data = object;
			var _defrend = can.ajax({
				url : url,
				type : "POST",
				dataType : "json",
				data : JSON.stringify(sign.signData(_data))
			});
			var _fail = fail;
			if (_fail == undefined) {
				_fail = function(message) {
					MF.Mask.toast(message);
				};
			}
			if (success == undefined)
				return _defrend;
			else
				return _defrend.then(success, _fail);
		};
		/*add by vidy.tu start 增加session验证数据
		*/
		var sign={};

		this.init=function(session){	
			sign._session=session;
		}
		sign.signUrl=function(url){
			var part= (url.indexOf('?')>0 ? '&':'?');
			var session = sign._session;
			for(var key in session){
				part = part + encodeURIComponent(key)+'='+encodeURIComponent(session[key])+'&';
			}
			var _url = url+part.slice(0,-1);
			return _url;
		}
		sign.signData=function(data){
			var session = sign._session;
			var postData={};
			for(var key in session){
				postData[key]=session[key];
			}
			if(data != undefined){
				postData['data']=data;
			}
			return postData;
		}
		//add by vidy.tu end
	}

	var _remote = new _Remote();
	return _remote;
});
