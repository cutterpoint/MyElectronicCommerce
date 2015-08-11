/* WebUploader 0.1.0 */
(function( window, undefined ) {
    /**
     * @fileOverview ���ڲ����������Ĵ��������[amd](https://github.com/amdjs/amdjs-api/wiki/AMD)ģ�鶨�巽ʽ��֯������
     *
     * AMD API �ڲ��ļ򵥲���ȫʵ�֣�����ԡ�ֻ�е�WebUploader���ϲ���һ���ļ���ʱ��Ż����롣
     */
    var internalAmd = (function( global, undefined ) {
            var modules = {},
    
                // �򵥲���ȫʵ��https://github.com/amdjs/amdjs-api/wiki/require
                require = function( deps, callback ) {
                    var args, len, i;
    
                    // ���deps�������飬��ֱ�ӷ���ָ��module
                    if ( typeof deps === 'string' ) {
                        return getModule( deps );
                    } else {
                        args = [];
                        for( len = deps.length, i = 0; i < len; i++ ) {
                            args.push( getModule( deps[ i ] ) );
                        }
    
                        return callback.apply( null, args );
                    }
                },
    
                // �ڲ���define����ʱ��֧�ֲ�ָ��id.
                define = function( id, deps, factory ) {
                    if ( arguments.length === 2 ) {
                        factory = deps;
                        deps = null;
                    }
    
                    if ( typeof id !== 'string' || !factory ) {
                        throw new Error('Define Error');
                    }
    
                    require( deps || [], function() {
                        setModule( id, factory, arguments );
                    });
                },
    
                // ����module, ����CommonJsд����
                setModule = function( id, factory, args ) {
                    var module = {
                            exports: factory
                        },
                        returned;
    
                    if ( typeof factory === 'function' ) {
                        args.length || (args = [ require, module.exports, module ]);
                        returned = factory.apply( null, args );
                        returned !== undefined && (module.exports = returned);
                    }
    
                    modules[ id ] = module.exports;
                },
    
                // ����id��ȡmodule
                getModule = function( id ) {
                    var module = modules[ id ] || global[ id ];
    
                    if ( !module ) {
                        throw new Error( '`' + id + '` is undefined' );
                    }
    
                    return module;
                };
    
            return {
                define: define,
                require: require,
    
                // ��¶���е�ģ�顣
                modules: modules
            };
        })( window ),
    
        /* jshint unused: false */
        require = internalAmd.require,
        define = internalAmd.define;

    /**
     * @fileOverview �����෽����
     */
    
    /**
     * Web Uploader�ڲ������ϸ˵���������ἰ�Ĺ����࣬��������`WebUploader`��������з��ʵ���
     *
     * As you know, Web Uploader��ÿ���ļ������ù�[AMD](https://github.com/amdjs/amdjs-api/wiki/AMD)�淶�е�`define`��֯������, ÿ��Module�����и�module id.
     * Ĭ��module id���ļ���·��������·������ת�������ֿռ�����WebUploader�С��磺
     *
     * * module `base`��WebUploader.Base
     * * module `file`: WebUploader.File
     * * module `lib/dnd`: WebUploader.Lib.Dnd
     * * module `runtime/html5/dnd`: WebUploader.Runtime.Html5.Dnd
     *
     *
     * �����ĵ�������ʡ��`WebUploader`ǰ׺��
     * @module WebUploader
     * @title WebUploader API�ĵ�
     */
    define( 'base', [
        'jQuery'
    ], function( $ ) {
    
        var noop = function() {},
            call = Function.call;
    
        // http://jsperf.com/uncurrythis
        // �����ﻯ
        function uncurryThis( fn ) {
            return function() {
                return call.apply( fn, arguments );
            };
        }
    
        function bindFn( fn, context ) {
            return function() {
                return fn.apply( context, arguments );
            };
        }
    
        function createObject( proto ) {
            var f;
    
            if ( Object.create ) {
                return Object.create( proto );
            } else {
                f = function() {};
                f.prototype = proto;
                return new f();
            }
        }
    
    
        /**
         * �����࣬�ṩһЩ�򵥳��õķ�����
         * @class Base
         */
        return {
    
            /**
             * @property {String} version ��ǰ�汾�š�
             */
            version: '0.1.0',
    
            /**
             * @property {jQuery|Zepto} $ ����������jQuery����Zepto����
             */
            $: $,
    
            /**
             * ����һ��[Deferred](http://api.jquery.com/category/deferred-object/)����
             * ��ϸ��Deferred�÷�˵���������jQuery��API�ĵ���
             *
             * Deferred�����ڹ��ӻص������о���Ҫ�õ�������������Ҫ�ȴ����첽������
             *
             *
             * @method Deferred
             * @grammar Base.Deferred() => Deferred
             * @example
             * // ���ļ���ʼ����ǰ��Щ�첽������
             * // WebUploader��ȴ����첽������ɺ󣬿�ʼ�����ļ���
             * Uploader.register({
             *     'before-send-file': 'doSomthingAsync'
             * }, {
             *
             *     doSomthingAsync: function() {
             *         var deferred = Base.Deferred();
             *
             *         // ģ��һ���첽������
             *         setTimeout(deferred.resolve, 2000);
             *
             *         return deferred.promise();
             *     }
             * });
             */
            Deferred: $.Deferred,
    
            /**
             * �жϴ���Ĳ����Ƿ�Ϊһ��promise����
             * @method isPromise
             * @grammar Base.isPromise( anything ) => Boolean
             * @param  {*}  anything ������
             * @return {Boolean}
             * @example
             * console.log( Base.isPromise() );    // => false
             * console.log( Base.isPromise({ key: '123' }) );    // => false
             * console.log( Base.isPromise( Base.Deferred().promise() ) );    // => true
             *
             * // DeferredҲ��һ��Promise
             * console.log( Base.isPromise( Base.Deferred() ) );    // => true
             */
            isPromise: function( anything ) {
                return anything && typeof anything.then === 'function';
            },
    
    
            /**
             * ����һ��promise����promise�����д����promise������˺���ɡ�
             * ��ϸ��鿴[����](http://api.jquery.com/jQuery.when/)��
             *
             * @method when
             * @grammar Base.when( promise1[, promise2[, promise3...]] ) => Promise
             */
            when: $.when,
    
            /**
             * @description  �򵥵�������������
             *
             * * `webkit`  webkit�汾�ţ���������Ϊ��webkit�ںˣ�������Ϊ`undefined`��
             * * `chrome`  chrome������汾�ţ���������Ϊchrome��������Ϊ`undefined`��
             * * `ie`  ie������汾�ţ���������Ϊ��ie��������Ϊ`undefined`��**�ݲ�֧��ie10+**
             * * `firefox`  firefox������汾�ţ���������Ϊ��firefox��������Ϊ`undefined`��
             * * `safari`  safari������汾�ţ���������Ϊ��safari��������Ϊ`undefined`��
             * * `opera`  opera������汾�ţ���������Ϊ��opera��������Ϊ`undefined`��
             *
             * @property {Object} [browser]
             */
            browser: (function( ua ) {
                var ret = {},
                    webkit = ua.match( /WebKit\/([\d.]+)/ ),
                    chrome = ua.match( /Chrome\/([\d.]+)/ ) ||
                        ua.match( /CriOS\/([\d.]+)/ ),
    
                    ie = ua.match( /MSIE\s([\d.]+)/ ),
                    firefox = ua.match( /Firefox\/([\d.]+)/ ),
                    safari = ua.match( /Safari\/([\d.]+)/ ),
                    opera = ua.match( /OPR\/([\d.]+)/ );
    
                webkit && (ret.webkit = parseFloat( webkit[ 1 ] ));
                chrome && (ret.chrome = parseFloat( chrome[ 1 ] ));
                ie && (ret.ie = parseFloat( ie[ 1 ] ));
                firefox && (ret.firefox = parseFloat( firefox[ 1 ] ));
                safari && (ret.safari = parseFloat( safari[ 1 ] ));
                opera && (ret.opera = parseFloat( opera[ 1 ] ));
    
                return ret;
            })( navigator.userAgent ),
    
            /**
             * ʵ��������֮��ļ̳С�
             * @method inherits
             * @grammar Base.inherits( super ) => child
             * @grammar Base.inherits( super, protos ) => child
             * @grammar Base.inherits( super, protos, statics ) => child
             * @param  {Class} super ����
             * @param  {Object | Function} [protos] ������߶�����������а���constructor�����ཫ���ô�����ֵ��
             * @param  {Function} [protos.constructor] ���๹��������ָ���Ļ�����������ʱ��ֱ��ִ�и��๹�����ķ�����
             * @param  {Object} [statics] ��̬���Ի򷽷���
             * @return {Class} �������ࡣ
             * @example
             * function Person() {
             *     console.log( 'Super' );
             * }
             * Person.prototype.hello = function() {
             *     console.log( 'hello' );
             * };
             *
             * var Manager = Base.inherits( Person, {
             *     world: function() {
             *         console.log( 'World' );
             *     }
             * });
             *
             * // ��Ϊû��ָ��������������Ĺ���������ִ�С�
             * var instance = new Manager();    // => Super
             *
             * // �̳��Ӹ���ķ���
             * instance.hello();    // => hello
             * instance.world();    // => World
             *
             * // �����__super__����ָ����
             * console.log( Manager.__super__ === Person );    // => true
             */
            inherits: function( Super, protos, staticProtos ) {
                var child;
    
                if ( typeof protos === 'function' ) {
                    child = protos;
                    protos = null;
                } else if ( protos && protos.hasOwnProperty('constructor') ) {
                    child = protos.constructor;
                } else {
                    child = function() {
                        return Super.apply( this, arguments );
                    };
                }
    
                // ���ƾ�̬����
                $.extend( true, child, Super, staticProtos || {} );
    
                /* jshint camelcase: false */
    
                // �������__super__����ָ���ࡣ
                child.__super__ = Super.prototype;
    
                // ����ԭ�ͣ����ԭ�ͷ��������ԡ�
                // ��ʱ��Object.createʵ�֡�
                child.prototype = createObject( Super.prototype );
                protos && $.extend( true, child.prototype, protos );
    
                return child;
            },
    
            /**
             * һ�������κ�����ķ���������������ֵ��Ĭ�ϵ�callback.
             * @method noop
             */
            noop: noop,
    
            /**
             * ����һ���µķ������˷�������ָ����`context`��ִ�С�
             * @grammar Base.bindFn( fn, context ) => Function
             * @method bindFn
             * @example
             * var doSomething = function() {
             *         console.log( this.name );
             *     },
             *     obj = {
             *         name: 'Object Name'
             *     },
             *     aliasFn = Base.bind( doSomething, obj );
             *
             *  aliasFn();    // => Object Name
             *
             */
            bindFn: bindFn,
    
            /**
             * ����Console.log������ڵĻ�����������һ��[�պ���loop](#WebUploader:Base.log)��
             * @grammar Base.log( args... ) => undefined
             * @method log
             */
            log: (function() {
                if ( window.console ) {
                    return bindFn( console.log, console );
                }
                return noop;
            })(),
    
            nextTick: (function() {
    
                return function( cb ) {
                    setTimeout( cb, 1 );
                };
    
                // @bug ����������ڵ�ǰ����ʱ��ͣ�ˡ�
                // var next = window.requestAnimationFrame ||
                //     window.webkitRequestAnimationFrame ||
                //     window.mozRequestAnimationFrame ||
                //     function( cb ) {
                //         window.setTimeout( cb, 1000 / 60 );
                //     };
    
                // // fix: Uncaught TypeError: Illegal invocation
                // return bindFn( next, window );
            })(),
    
            /**
             * ��[uncurrythis](http://www.2ality.com/2011/11/uncurrying-this.html)������slice������
             * �����������������ת�����������
             * @grammar Base.slice( target, start[, end] ) => Array
             * @method slice
             * @example
             * function doSomthing() {
             *     var args = Base.slice( arguments, 1 );
             *     console.log( args );
             * }
             *
             * doSomthing( 'ignored', 'arg2', 'arg3' );    // => Array ["arg2", "arg3"]
             */
            slice: uncurryThis( [].slice ),
    
            /**
             * ����Ψһ��ID
             * @method guid
             * @grammar Base.guid() => String
             * @grammar Base.guid( prefx ) => String
             */
            guid: (function() {
                var counter = 0;
    
                return function( prefix ) {
                    var guid = (+new Date()).toString( 32 ),
                        i = 0;
    
                    for ( ; i < 5; i++ ) {
                        guid += Math.floor( Math.random() * 65535 ).toString( 32 );
                    }
    
                    return (prefix || 'wu_') + guid + (counter++).toString( 32 );
                };
            })(),
    
            /**
             * ��ʽ���ļ���С, ����ɴ���λ���ַ���
             * @method formatSize
             * @grammar Base.formatSize( size ) => String
             * @grammar Base.formatSize( size, pointLength ) => String
             * @grammar Base.formatSize( size, pointLength, units ) => String
             * @param {Number} size �ļ���С
             * @param {Number} [pointLength=2] ��ȷ����С��������
             * @param {Array} [units=[ 'B', 'K', 'M', 'G', 'TB' ]] ��λ���顣���ֽڣ���ǧ�ֽڣ�һֱ����ָ���������λ��������ָֻ���˵���K(ǧ�ֽ�)��ͬʱ�ļ���С����M, �˷����������������ʾ�ɶ���K.
             * @example
             * console.log( Base.formatSize( 100 ) );    // => 100B
             * console.log( Base.formatSize( 1024 ) );    // => 1.00K
             * console.log( Base.formatSize( 1024, 0 ) );    // => 1K
             * console.log( Base.formatSize( 1024 * 1024 ) );    // => 1.00M
             * console.log( Base.formatSize( 1024 * 1024 * 1024 ) );    // => 1.00G
             * console.log( Base.formatSize( 1024 * 1024 * 1024, 0, ['B', 'KB', 'MB'] ) );    // => 1024MB
             */
            formatSize: function( size, pointLength, units ) {
                var unit;
    
                units = units || [ 'B', 'K', 'M', 'G', 'TB' ];
    
                while ( (unit = units.shift()) && size > 1024 ) {
                    size = size / 1024;
                }
    
                return (unit === 'B' ? size : size.toFixed( pointLength || 2 )) +
                        unit;
            }
        };
    });

    /**
     * @fileOverview Mediator
     */
    define( 'mediator', [
        'base'
    ], function( Base ) {
        var $ = Base.$,
            slice = [].slice,
            separator = /\s+/,
            protos;
    
        // �����������˳��¼�handlers.
        function findHandlers( arr, name, callback, context ) {
            return $.grep( arr, function( handler ) {
                return handler &&
                        (!name || handler.e === name) &&
                        (!callback || handler.cb === callback ||
                        handler.cb._cb === callback) &&
                        (!context || handler.ctx === context);
            });
        }
    
        function eachEvent( events, callback, iterator ) {
            // ��֧�ֶ���ֻ֧�ֶ��event�ÿո����
            $.each( (events || '').split( separator ), function( _, key ) {
                iterator( key, callback );
            });
        }
    
        function triggerHanders( events, args ) {
            var stoped = false,
                i = -1,
                len = events.length,
                handler;
    
            while ( ++i < len ) {
                handler = events[ i ];
    
                if ( handler.cb.apply( handler.ctx2, args ) === false ) {
                    stoped = true;
                    break;
                }
            }
    
            return !stoped;
        }
    
        protos = {
    
            /**
             * ���¼���
             *
             * `callback`������ִ��ʱ��arguments������Դ��trigger��ʱ��Я���Ĳ�������
             * ```javascript
             * var obj = {};
             *
             * // ʹ��obj���¼���Ϊ
             * Mediator.installTo( obj );
             *
             * obj.on( 'testa', function( arg1, arg2 ) {
             *     console.log( arg1, arg2 ); // => 'arg1', 'arg2'
             * });
             *
             * obj.trigger( 'testa', 'arg1', 'arg2' );
             * ```
             *
             * ���`callback`�У�ĳһ������`return false`�ˣ������������`callback`�����ᱻִ�е���
             * �л�Ӱ�쵽`trigger`�����ķ���ֵ��Ϊ`false`��
             *
             * `on`�������������һ�������¼�`all`, �������е��¼�����������Ӧ����ͬʱ����`callback`�е�arguments��һ����ͬ����
             * ���ǵ�һ������Ϊ`type`����¼��ǰ��ʲô�¼��ڴ���������`callback`�����ȼ��Ƚŵͣ���������`callback`ִ����󴥷���
             * ```javascript
             * obj.on( 'all', function( type, arg1, arg2 ) {
             *     console.log( type, arg1, arg2 ); // => 'testa', 'arg1', 'arg2'
             * });
             * ```
             *
             * @method on
             * @grammar on( name, callback[, context] ) => self
             * @param  {String}   name     �¼�����֧�ֶ���¼��ÿո����
             * @param  {Function} callback �¼�������
             * @param  {Object}   [context]  �¼��������������ġ�
             * @return {self} ��������������ʽ
             * @chainable
             * @class Mediator
             */
            on: function( name, callback, context ) {
                var me = this,
                    set;
    
                if ( !callback ) {
                    return this;
                }
    
                set = this._events || (this._events = []);
    
                eachEvent( name, callback, function( name, callback ) {
                    var handler = { e: name };
    
                    handler.cb = callback;
                    handler.ctx = context;
                    handler.ctx2 = context || me;
                    handler.id = set.length;
    
                    set.push( handler );
                });
    
                return this;
            },
    
            /**
             * ���¼����ҵ�handlerִ������Զ�����󶨡�
             * @method once
             * @grammar once( name, callback[, context] ) => self
             * @param  {String}   name     �¼���
             * @param  {Function} callback �¼�������
             * @param  {Object}   [context]  �¼��������������ġ�
             * @return {self} ��������������ʽ
             * @chainable
             */
            once: function( name, callback, context ) {
                var me = this;
    
                if ( !callback ) {
                    return me;
                }
    
                eachEvent( name, callback, function( name, callback ) {
                    var once = function() {
                            me.off( name, once );
                            return callback.apply( context || me, arguments );
                        };
    
                    once._cb = callback;
                    me.on( name, once, context );
                });
    
                return me;
            },
    
            /**
             * ����¼���
             * @method off
             * @grammar off( [name[, callback[, context] ] ] ) => self
             * @param  {String}   [name]     �¼���
             * @param  {Function} [callback] �¼�������
             * @param  {Object}   [context]  �¼��������������ġ�
             * @return {self} ��������������ʽ
             * @chainable
             */
            off: function( name, cb, ctx ) {
                var events = this._events;
    
                if ( !events ) {
                    return this;
                }
    
                if ( !name && !cb && !ctx ) {
                    this._events = [];
                    return this;
                }
    
                eachEvent( name, cb, function( name, cb ) {
                    $.each( findHandlers( events, name, cb, ctx ), function() {
                        delete events[ this.id ];
                    });
                });
    
                return this;
            },
    
            /**
             * �����¼�
             * @method trigger
             * @grammar trigger( name[, args...] ) => self
             * @param  {String}   type     �¼���
             * @param  {*} [...] �������
             * @return {Boolean} ���handler��return false�ˣ��򷵻�false, ���򷵻�true
             */
            trigger: function( type ) {
                var args, events, allEvents;
    
                if ( !this._events || !type ) {
                    return this;
                }
    
                args = slice.call( arguments, 1 );
                events = findHandlers( this._events, type );
                allEvents = findHandlers( this._events, 'all' );
    
                return triggerHanders( events, args ) &&
                        triggerHanders( allEvents, arguments );
            }
        };
    
        /**
         * �н��ߣ��������Ǹ�������������ͨ��[installTo](#WebUploader:Mediator:installTo)������ʹ�κζ���߱��¼���Ϊ��
         * ��ҪĿ���Ǹ���ģ����ģ��֮��ĺ�����������϶ȡ�
         *
         * @class Mediator
         */
        return $.extend({
    
            /**
             * ����ͨ������ӿڣ�ʹ�κζ���߱��¼����ܡ�
             * @method installTo
             * @param  {Object} obj ��Ҫ�߱��¼���Ϊ�Ķ���
             * @return {Object} ����obj.
             */
            installTo: function( obj ) {
                return $.extend( obj, protos );
            }
    
        }, protos );
    });

    /**
     * @fileOverview Uploader�ϴ���
     */
    define( 'uploader', [
        'base',
        'mediator'
    ], function( Base, Mediator ) {
    
        var $ = Base.$;
    
        /**
         * �ϴ�����ࡣ
         * @class Uploader
         * @constructor
         * @grammar new Uploader( opts ) => Uploader
         * @example
         * var uploader = WebUploader.Uploader({
         *     swf: 'path_of_swf/Uploader.swf',
         *
         *     // �����Ƭ�ϴ���
         *     chunked: true
         * });
         */
        function Uploader( opts ) {
            this.options = $.extend( true, {}, Uploader.options, opts );
            this._init( this.options );
        }
    
        // default Options
        // widgets������Ӧ��չ
        Uploader.options = {};
        Mediator.installTo( Uploader.prototype );
    
        // ������Ӵ�����ʽ������
        $.each({
            upload: 'start-upload',
            stop: 'stop-upload',
            getFile: 'get-file',
            getFiles: 'get-files',
            // addFile: 'add-file',
            // addFiles: 'add-file',
            removeFile: 'remove-file',
            skipFile: 'skip-file',
            retry: 'retry',
            isInProgress: 'is-in-progress',
            makeThumb: 'make-thumb',
            getDimension: 'get-dimension',
            addButton: 'add-btn',
            getRuntimeType: 'get-runtime-type',
            refresh: 'refresh',
            disable: 'disable',
            enable: 'enable'
        }, function( fn, command ) {
            Uploader.prototype[ fn ] = function() {
                return this.request( command, arguments );
            };
        });
    
        $.extend( Uploader.prototype, {
            state: 'pending',
    
            _init: function( opts ) {
                var me = this;
    
                me.request( 'init', opts, function() {
                    me.state = 'ready';
                    me.trigger('ready');
                });
            },
    
            /**
             * ��ȡ��������Uploader�����
             * @method option
             * @grammar option( key ) => *
             * @grammar option( key, val ) => self
             * @example
             *
             * // ��ʼ״̬ͼƬ�ϴ�ǰ����ѹ��
             * var uploader = new WebUploader.Uploader({
             *     resize: null;
             * });
             *
             * // �޸ĺ�ͼƬ�ϴ�ǰ�����Խ�ͼƬѹ����1600 * 1600
             * uploader.options( 'resize', {
             *     width: 1600,
             *     height: 1600
             * });
             */
            option: function( key, val ) {
                var opts = this.options;
    
                // setter
                if ( arguments.length > 1 ) {
    
                    if ( $.isPlainObject( val ) &&
                            $.isPlainObject( opts[ key ] ) ) {
                        $.extend( opts[ key ], val );
                    } else {
                        opts[ key ] = val;
                    }
    
                } else {    // getter
                    return key ? opts[ key ] : opts;
                }
            },
    
            /**
             * ��ȡ�ļ�ͳ����Ϣ������һ������һ����Ϣ�Ķ���
             * * `successNum` �ϴ��ɹ����ļ���
             * * `uploadFailNum` �ϴ�ʧ�ܵ��ļ���
             * * `cancelNum` ��ɾ�����ļ���
             * * `invalidNum` ��Ч���ļ���
             * * `queueNum` ���ڶ����е��ļ���
             * @method getStats
             * @grammar getStats() => Object
             */
            getStats: function() {
                // return this._mgr.getStats.apply( this._mgr, arguments );
                var stats = this.request('get-stats');
    
                return {
                    successNum: stats.numOfSuccess,
    
                    // who care?
                    // queueFailNum: 0,
                    cancelNum: stats.numOfCancel,
                    invalidNum: stats.numOfInvalid,
                    uploadFailNum: stats.numOfUploadFailed,
                    queueNum: stats.numOfQueue
                };
            },
    
            // ��Ҫ��д�˷�������֧��opts.onEvent��instance.onEvent�Ĵ�����
            trigger: function( type/*, args...*/ ) {
                var args = [].slice.call( arguments, 1 ),
                    opts = this.options,
                    name = 'on' + type.substring( 0, 1 ).toUpperCase() +
                        type.substring( 1 );
    
                if ( Mediator.trigger.apply( this, arguments ) === false ) {
                    return false;
                }
    
                if ( $.isFunction( opts[ name ] ) &&
                        opts[ name ].apply( this, args ) === false ) {
                    return false;
                }
    
                if ( $.isFunction( this[ name ] ) &&
                        this[ name ].apply( this, args ) === false ) {
                    return false;
                }
    
                return true;
            },
    
            // widgets/widget.js������˷�������ϸ�ĵ���
            request: Base.noop,
    
            reset: function() {
                // @todo
            }
        });
    
        /**
         * ����Uploaderʵ������ͬ��new Uploader( opts );
         * @method create
         * @class Base
         * @static
         * @grammar Base.create( opts ) => Uploader
         */
        Base.create = function( opts ) {
            return new Uploader( opts );
        };
    
        // ��¶Uploader������ͨ��������չҵ���߼���
        Base.Uploader = Uploader;
    
        return Uploader;
    });

    /**
     * @fileOverview Runtime������������Runtime��ѡ��, ����
     */
    define( 'runtime/runtime', [
        'base',
        'mediator'
    ], function( Base, Mediator ) {
    
        var $ = Base.$,
            factories = {},
    
            // ��ȡ����ĵ�һ��key
            getFirstKey = function( obj ) {
                for ( var key in obj ) {
                    if ( obj.hasOwnProperty( key ) ) {
                        return key;
                    }
                }
                return null;
            };
    
        // �ӿ��ࡣ
        function Runtime( options ) {
            this.options = $.extend({
                container: document.body
            }, options );
            this.uid = Base.guid('rt_');
        }
    
        $.extend( Runtime.prototype, {
    
            getContainer: function() {
                var opts = this.options,
                    parent, container;
    
                if ( this._container ) {
                    return this._container;
                }
    
                parent = opts.container || $( document.body );
                container = $( document.createElement('div') );
    
                container.attr( 'id', 'rt_' + this.uid );
                container.css({
                    position: 'absolute',
                    top: '0px',
                    left: '0px',
                    width: '1px',
                    height: '1px',
                    overflow: 'hidden'
                });
    
                parent.append( container );
                parent.addClass('webuploader-container');
                this._container = container;
                return container;
            },
    
            init: Base.noop,
            exec: Base.noop,
    
            destroy: function() {
                if ( this._container ) {
                    this._container.parentNode.removeChild( this.__container );
                }
    
                this.off();
            }
        });
    
        Runtime.orders = 'html5,flash';
    
    
        /**
         * ���Runtimeʵ�֡�
         * @param {String} type    ����
         * @param {Runtime} factory ����Runtimeʵ�֡�
         */
        Runtime.addRuntime = function( type, factory ) {
            factories[ type ] = factory;
        };
    
        Runtime.hasRuntime = function( type ) {
            return !!(type ? factories[ type ] : getFirstKey( factories ));
        };
    
        Runtime.create = function( opts, orders ) {
            var type, runtime;
    
            orders = orders || Runtime.orders;
            $.each( orders.split( /\s*,\s*/g ), function() {
                if ( factories[ this ] ) {
                    type = this;
                    return false;
                }
            });
    
            type = type || getFirstKey( factories );
    
            if ( !type ) {
                throw new Error('Runtime Error');
            }
    
            runtime = new factories[ type ]( opts );
            return runtime;
        };
    
        Mediator.installTo( Runtime.prototype );
        return Runtime;
    });

    /**
     * @fileOverview Runtime������������Runtime��ѡ��, ����
     */
    define( 'runtime/client', [
        'base',
        'mediator',
        'runtime/runtime'
    ], function( Base, Mediator, Runtime ) {
    
        var cache = (function() {
                var obj = {};
    
                return {
                    add: function( runtime ) {
                        obj[ runtime.uid ] = runtime;
                    },
    
                    get: function( ruid ) {
                        var i;
    
                        if ( ruid ) {
                            return obj[ ruid ];
                        }
    
                        for ( i in obj ) {
                            return obj[ i ];
                        }
    
                        return null;
                    },
    
                    remove: function( runtime ) {
                        delete obj[ runtime.uid ];
                    },
    
                    has: function() {
                        return !!this.get.apply( this, arguments );
                    }
                };
            })();
    
        function RuntimeClient( component, standalone ) {
            var deferred = Base.Deferred(),
                runtime;
    
            this.uid = Base.guid('client_');
    
            this.runtimeReady = function( cb ) {
                return deferred.done( cb );
            };
    
            this.connectRuntime = function( opts, cb ) {
                if ( runtime ) {
                    return;
                }
    
                deferred.done( cb );
    
                if ( typeof opts === 'string' && cache.get( opts ) ) {
                    runtime = cache.get( opts );
    
                // ��filePickerֻ�ܶ������ڣ����ܹ��á�
                } else if ( !standalone && cache.has() ) {
                    runtime = cache.get();
                }
    
                if ( !runtime ) {
                    runtime = Runtime.create( opts, opts.runtimeOrder );
                    cache.add( runtime );
                    runtime.promise = deferred.promise();
                    runtime.once( 'ready', deferred.resolve );
                    runtime.init();
                    runtime.client = 1;
                    return runtime;
                }
    
                runtime.promise.then( deferred.resolve );
                runtime.client++;
                return runtime;
            };
    
            this.getRuntime = function() {
                return runtime;
            };
    
            this.disconnectRuntime = function() {
                if ( !runtime ) {
                    return;
                }
    
                runtime.client--;
    
                if ( runtime.client <= 0 ) {
                    cache.remove( runtime );
                    delete runtime.promise;
                    runtime.destroy();
                }
    
                runtime = null;
            };
    
            this.exec = function() {
                if ( !runtime ) {
                    return;
                }
    
                var args = Base.slice( arguments );
                component && args.unshift( component );
    
                return runtime.exec.apply( this, args );
            };
    
            this.getRuid = function() {
                return runtime && runtime.uid;
            };
    
            this.destroy = (function( destroy ) {
                return function() {
                    destroy && destroy.apply( this, arguments );
                    this.trigger('destroy');
                    this.off();
                    this.exec('destroy');
                    this.disconnectRuntime();
                };
            })( this.destroy );
        }
    
        Mediator.installTo( RuntimeClient.prototype );
        return RuntimeClient;
    });

    /**
     * @fileOverview Blob
     */
    define( 'lib/blob', [
        'base',
        'runtime/client'
    ], function( Base, RuntimeClient ) {
    
        function Blob( ruid, source ) {
            var me = this;
    
            me.source = source;
            me.ruid = ruid;
    
            RuntimeClient.call( me, 'Blob' );
    
            this.uid = source.uid || this.uid;
            this.type = source.type || '';
            this.size = source.size || 0;
    
            if ( ruid ) {
                me.connectRuntime( ruid );
            }
        }
    
        Base.inherits( RuntimeClient, {
            constructor: Blob,
    
            slice: function( start, end ) {
                return this.exec( 'slice', start, end );
            },
    
            getSource: function() {
                return this.source;
            }
        });
    
        return Blob;
    });

    /**
     * @fileOverview File
     */
    define( 'lib/file', [
        'base',
        'lib/blob'
    ], function( Base, Blob ) {
    
        var uid = 0,
            rExt = /\.([^.]+)$/;
    
        function File( ruid, file ) {
            var ext;
    
            Blob.apply( this, arguments );
            this.name = file.name || ('untitled' + uid++);
            ext = rExt.exec( file.name ) ? RegExp.$1.toLowerCase() : '';
    
            if ( !this.type &&  ~'jpg,jpeg,png,gif,bmp'.indexOf( ext ) ) {
                this.type = 'image/' + ext;
            }
    
            this.ext = ext;
            this.lastModifiedDate = file.lastModifiedDate ||
                    (new Date()).toLocaleString();
        }
    
        return Base.inherits( Blob, File );
    });

    /**
     * @fileOverview ������Ϣ
     */
    define( 'lib/filepicker', [
        'base',
        'runtime/client',
        'lib/file'
    ], function( Base, RuntimeClent, File ) {
    
        var $ = Base.$;
    
        function FilePicker( opts ) {
    
            opts = this.options = $.extend({}, FilePicker.options, opts );
            opts.container = $( opts.id );
    
            if ( !opts.container.length ) {
                throw new Error('��ťָ������');
            }
    
            opts.label = opts.label || opts.container.text() || 'ѡ���ļ�';
            opts.button = $( opts.button || document.createElement('div') );
            opts.button.text( opts.label );
            opts.container.html( opts.button );
    
            RuntimeClent.call( this, 'FilePicker', true );
        }
    
        FilePicker.options = {
            button: null,
            container: null,
            label: null,
            multiple: true,
            accept: null
        };
    
        Base.inherits( RuntimeClent, {
            constructor: FilePicker,
    
            init: function() {
                var me = this,
                    opts = me.options,
                    button = opts.button;
    
                button.addClass('webuploader-pick');
    
                me.on( 'all', function( type ) {
                    var files;
    
                    switch ( type ) {
                        case 'mouseenter':
                            button.addClass('webuploader-pick-hover');
                            break;
    
                        case 'mouseleave':
                            button.removeClass('webuploader-pick-hover');
                            break;
    
                        case 'change':
                            files = me.exec('getFiles');
                            me.trigger( 'select', $.map( files, function( file ) {
                                return new File( me.getRuid(), file );
                            }) );
                            break;
                    }
                });
    
                me.connectRuntime( opts, function() {
                    me.refresh();
                    me.exec( 'init', opts );
                });
    
                $( window ).on( 'resize', function() {
                    me.refresh();
                });
            },
    
            refresh: function() {
                var shimContainer = this.getRuntime().getContainer(),
                    button = this.options.button,
                    width = button.outerWidth(),
                    height = button.outerHeight(),
                    pos = button.offset();
    
                width && shimContainer.css({
                    width: width + 'px',
                    height: height + 'px'
                }).offset( pos );
            },
    
            destroy: function() {
                if ( this.runtime ) {
                    this.exec('destroy');
                    this.disconnectRuntime();
                }
            }
        });
    
        return FilePicker;
    });

    /**
     * @fileOverview ������ࡣ
     */
    define( 'widgets/widget', [
        'base',
        'uploader'
    ], function( Base, Uploader ) {
    
        var $ = Base.$,
            _init = Uploader.prototype._init,
            IGNORE = {},
            widgetClass = [];
    
        function isArrayLike( obj ) {
            if ( !obj ) {
                return false;
            }
    
            var length = obj.length,
                type = $.type( obj );
    
            if ( obj.nodeType === 1 && length ) {
                return true;
            }
    
            return type === 'array' || type !== 'function' && type !== 'string' &&
                    (length === 0 || typeof length === 'number' && length > 0 &&
                    (length - 1) in obj);
        }
    
        function Widget( uploader ) {
            this.owner = uploader;
            this.options = uploader.options;
        }
    
        $.extend( Widget.prototype, {
    
            init: Base.noop,
    
            // ��Backbone���¼���������������uploaderʵ���ϵ��¼�
            // widgetֱ���޷������¼����¼�ֻ��ͨ��uploader������
            invoke: function( apiName, args ) {
    
                /*
                    {
                        'make-thumb': 'makeThumb'
                    }
                 */
                var map = this.responseMap;
    
                // �����API��Ӧ���������
                if ( !map || !(apiName in map) || !(map[ apiName ] in this) ||
                        !$.isFunction( this[ map[ apiName ] ] ) ) {
    
                    return IGNORE;
                }
    
                return this[ map[ apiName ] ].apply( this, args );
    
            },
    
            /**
             * �������������`callback`����`handler`�з���`promise`ʱ������һ��������`handler`�е�promise����ɺ���ɵ���`promise`��
             * @method request
             * @grammar request( command, args ) => * | Promise
             * @grammar request( command, args, callback ) => Promise
             * @for  Uploader
             */
            request: function() {
                return this.owner.request.apply( this.owner, arguments );
            }
        });
    
        // ��չUploader.
        $.extend( Uploader.prototype, {
    
            // ��д_init������ʼ��widgets
            _init: function() {
                var me = this,
                    widgets = me._widgets = [];
    
                $.each( widgetClass, function( _, klass ) {
                    widgets.push( new klass( me ) );
                });
    
                return _init.apply( me, arguments );
            },
    
            request: function( apiName, args, callback ) {
                var i = 0,
                    widgets = this._widgets,
                    len = widgets.length,
                    rlts = [],
                    dfds = [],
                    widget, rlt;
    
                args = isArrayLike( args ) ? args : [ args ];
    
                for ( ; i < len; i++ ) {
                    widget = widgets[ i ];
                    rlt = widget.invoke( apiName, args );
    
                    if ( rlt !== IGNORE ) {
    
                        // Deferred����
                        if ( Base.isPromise( rlt ) ) {
                            dfds.push( rlt );
                        } else {
                            rlts.push( rlt );
                        }
                    }
                }
    
                // �����callback�������첽��ʽ��
                if ( callback || dfds.length ) {
                    return Base.when.apply( Base, dfds )
    
                            // ����Ҫ����ɾ����ɾ���˻���ѭ����
                            // ��ִ֤��˳����callback��������һ��tick��ִ�С�
                            .then(function() {
                                var deferred = Base.Deferred(),
                                    args = arguments;
    
                                setTimeout(function() {
                                    deferred.resolve.apply( deferred, args );
                                }, 1 );
    
                                return deferred.promise();
                            })
                            .then( callback || Base.noop );
                } else {
                    return rlts[ 0 ];
                }
            }
        });
    
        /**
         * ������
         * @param  {object} widgetProto ���ԭ�ͣ����캯��ͨ��constructor���Զ���
         * @param  {object} responseMap API�����뺯��ʵ�ֵ�ӳ��
         * @example
         *     Uploader.register( {
         *         init: function( options ) {},
         *         makeThumb: function() {}
         *     }, {
         *         'make-thumb': 'makeThumb'
         *     } );
         */
        Uploader.register = Widget.register = function( responseMap, widgetProto ) {
            var map = { init: 'init' },
                klass;
    
            if ( arguments.length === 1 ) {
                widgetProto = responseMap;
                widgetProto.responseMap = map;
            } else {
                widgetProto.responseMap = $.extend( map, responseMap );
            }
    
            klass = Base.inherits( Widget, widgetProto );
            widgetClass.push( klass );
    
            return klass;
        };
    
        return Widget;
    });

    /**
     * @fileOverview �ļ�ѡ�����
     */
    define( 'widgets/filepicker', [
        'base',
        'uploader',
        'lib/filepicker',
        'widgets/widget'
    ], function( Base, Uploader, FilePicker ) {
    
        Base.$.extend( Uploader.options, {
    
            /**
             * @property {Selector | Object} [pick=undefined]
             * @namespace options
             * @for Uploader
             * @description ָ��ѡ���ļ��İ�ť��������ָ���򲻴�����ť��
             *
             * * `id` {Seletor} ָ��ѡ���ļ��İ�ť��������ָ���򲻴�����ť��
             * * `label` {String} ָ����ť���֡���ָ��ʱ���ȴ�ָ���������п��Ƿ��Դ����֡�
             * * `multiple` {Boolean} �Ƿ���ͬʱѡ�����ļ�������
             */
            pick: null,
    
            /**
             * @property {Arroy} [accept=null]
             * @namespace options
             * @for Uploader
             * @description ָ��������Щ���͵��ļ��� ����Ŀǰ����extתmimeType������������Ҫ�ֿ�ָ����
             *
             * * `title` {String} ��������
             * * `extensions` {String} ������ļ���׺�������㣬����ö��ŷָ
             * * `mimeTypes` {String} ����ö��ŷָ
             *
             * �磺
             *
             * ```
             * {
             *     title: 'Images',
             *     extensions: 'gif,jpg,jpeg,bmp,png',
             *     mimeTypes: 'image/*'
             * }
             * ```
             */
            accept: null/*{
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }*/
        });
    
        return Uploader.register({
            'add-btn': 'addButton',
            'refresh': 'refresh'
        }, {
    
            init: function( opts ) {
                this.pickers = [];
                return opts.pick && this.addButton( opts.pick );
            },
    
            refresh: function() {
                $.each( this.pickers, function() {
                    this.refresh();
                });
            },
    
            /**
             * @method addButton
             * @for Uploader
             * @grammar addButton( pick ) => Promise
             * @description
             * ����ļ�ѡ��ť�����һ����ť��������Ҫ���ô˷�������ӡ�������[options.pick](#WebUploader:Uploader:options)һ�¡�
             * @example
             * uploader.addButton({
             *     id: '#btnContainer',
             *     label: 'ѡ���ļ�'
             * });
             */
            addButton: function( pick ) {
                var me = this,
                    opts = me.options,
                    accept = opts.accept,
                    options, picker, deferred;
    
                if ( !pick ) {
                    return;
                }
    
                deferred = Base.Deferred();
    
                if ( typeof pick === 'string' ) {
                    pick = {
                        id: pick
                    };
                }
    
                options = $.extend({}, pick, {
                    accept: $.isPlainObject( accept ) ? [ accept ] : accept,
                    swf: opts.swf,
                    runtimeOrder: opts.runtimeOrder
                });
    
                picker = new FilePicker( options );
    
                picker.once( 'ready', deferred.resolve );
                picker.on( 'select', function( files ) {
                    me.owner.request( 'add-file', [ files ]);
                });
                picker.init();
    
                this.pickers.push( picker );
    
                return deferred.promise();
            }
        });
    });

    /**
     * @fileOverview �ļ����Է�װ
     */
    define( 'file', [
        'base',
        'mediator'
    ], function( Base, Mediator ) {
    
        var $ = Base.$,
            idPrefix = 'WU_FILE_',
            idSuffix = 0,
            rExt = /\.([^.]+)$/,
            statusMap = {};
    
        function gid() {
            return idPrefix + idSuffix++;
        }
    
        /**
         * �ļ���
         * @class File
         * @constructor ���캯��
         * @grammar new File( source ) => File
         * @param {Lib.File} source [lib.File](#Lib.File)ʵ��, ��source�����Ǵ���Runtime��Ϣ�ġ�
         */
        function WUFile( source ) {
    
            /**
             * �ļ�����������չ������׺��
             * @property name
             * @type {string}
             */
            this.name = source.name || 'Untitled';
    
            /**
             * �ļ�������ֽڣ�
             * @property size
             * @type {uint}
             * @default 0
             */
            this.size = source.size || 0;
    
            /**
             * �ļ�MIMETYPE���ͣ����ļ����͵Ķ�Ӧ��ϵ��ο�[http://t.cn/z8ZnFny](http://t.cn/z8ZnFny)
             * @property type
             * @type {string}
             * @default 'image/png'
             */
            this.type = source.type || 'image/png';
    
            /**
             * �ļ�����޸�����
             * @property lastModifiedDate
             * @type {int}
             * @default ��ǰʱ���
             */
            this.lastModifiedDate = source.lastModifiedDate || (new Date() * 1);
    
            /**
             * �ļ�ID��ÿ���������ΨһID�����ļ����޹�
             * @property id
             * @type {string}
             */
            this.id = gid();
    
            /**
             * �ļ���չ����ͨ���ļ�����ȡ������test.png����չ��Ϊpng
             * @property ext
             * @type {string}
             */
            this.ext = rExt.exec( this.name ) ? RegExp.$1 : '';
    
    
            /**
             * ״̬����˵�����ڲ�ͬ��status�ﾳ���в�ͬ����;��
             * @property statusText
             * @type {string}
             */
            this.statusText = '';
    
            // �洢�ļ�״̬����ֹͨ������ֱ���޸�
            statusMap[ this.id ] = WUFile.Status.INITED;
    
            this.source = source;
            this.loaded = 0;
    
            this.on( 'error', function( msg ) {
                this.setStatus( WUFile.Status.ERROR, msg );
            });
        }
    
        $.extend( WUFile.prototype, {
    
            /**
             * ����״̬��״̬�仯ʱ�ᴥ��`change`�¼���
             * @method setStatus
             * @grammar setStatus( status[, statusText] );
             * @param {File.Status|String} status [�ļ�״ֵ̬](#WebUploader:File:File.Status)
             * @param {String} [statusText=''] ״̬˵��������errorʱʹ�ã���http, abort,server�������������ʲôԭ�����ļ�����
             */
            setStatus: function( status, text ) {
    
                var prevStatus = statusMap[ this.id ];
    
                typeof text !== 'undefined' && (this.statusText = text);
    
                if ( status !== prevStatus ) {
                    statusMap[ this.id ] = status;
                    /**
                     * �ļ�״̬�仯
                     * @event statuschange
                     */
                    this.trigger( 'statuschange', status, prevStatus );
                }
    
            },
    
            /**
             * ��ȡ�ļ�״̬
             * @return {File.Status}
             * @example
                     �ļ�״̬����������¼������ͣ�
                     {
                         // ��ʼ��
                        INITED:     0,
                        // �������
                        QUEUED:     1,
                        // �����ϴ�
                        PROGRESS:     2,
                        // �ϴ�����
                        ERROR:         3,
                        // �ϴ��ɹ�
                        COMPLETE:     4,
                        // �ϴ�ȡ��
                        CANCELLED:     5
                    }
             */
            getStatus: function() {
                return statusMap[ this.id ];
            },
    
            /**
             * ��ȡ�ļ�ԭʼ��Ϣ��
             * @return {*}
             */
            getSource: function() {
                return this.source;
            },
    
            destory: function() {
                delete statusMap[ this.id ];
            }
        });
    
        Mediator.installTo( WUFile.prototype );
    
        /**
         * �ļ�״ֵ̬������������¼������ͣ�
         * * `inited` ��ʼ״̬
         * * `queued` �Ѿ��������, �ȴ��ϴ�
         * * `progress` �ϴ���
         * * `complete` �ϴ���ɡ�
         * * `error` �ϴ�����������
         * * `interrupt` �ϴ��жϣ���������
         * * `invalid` �ļ����ϸ񣬲��������ϴ������Զ��Ӷ������Ƴ���
         * * `cancelled` �ļ����Ƴ���
         * @property {Object} Status
         * @namespace File
         * @class File
         * @static
         */
        WUFile.Status = {
            INITED:     'inited',    // ��ʼ״̬
            QUEUED:     'queued',    // �Ѿ��������, �ȴ��ϴ�
            PROGRESS:   'progress',    // �ϴ���
            ERROR:      'error',    // �ϴ�����������
            COMPLETE:   'complete',    // �ϴ���ɡ�
            CANCELLED:  'cancelled',    // �ϴ�ȡ����
            INTERRUPT:  'interrupt',    // �ϴ��жϣ���������
            INVALID:    'invalid'    // �ļ����ϸ񣬲��������ϴ���
        };
    
        return WUFile;
    });

    /**
     * @fileOverview ������Ϣ
     */
    define( 'lib/dnd', [
        'base',
        'mediator',
        'runtime/client'
    ], function( Base, Mediator, RuntimeClent ) {
    
        var $ = Base.$;
    
        function DragAndDrop( opts ) {
            opts = this.options = $.extend({}, DragAndDrop.options, opts );
    
            opts.container = $( opts.container );
    
            if ( !opts.container.length ) {
                return;
            }
    
            RuntimeClent.call( this, 'DragAndDrop' );
        }
    
        DragAndDrop.options = {
            accept: null,
            disableGlobalDnd: true
        };
    
        Base.inherits( RuntimeClent, {
            constructor: DragAndDrop,
    
            init: function() {
                var me = this;
    
                me.connectRuntime( me.options, function() {
                    me.exec('init');
                });
            },
    
            destroy: function() {
                this.disconnectRuntime();
            }
        });
    
        Mediator.installTo( DragAndDrop.prototype );
    
        return DragAndDrop;
    });

    /**
     * @fileOverview ������Ϣ
     */
    define( 'lib/filepaste', [
        'base',
        'mediator',
        'runtime/client'
    ], function( Base, Mediator, RuntimeClent ) {
    
        var $ = Base.$;
    
        function FilePaste( opts ) {
            opts = this.options = $.extend({}, opts );
            opts.container = $( opts.container || document.body );
            RuntimeClent.call( this, 'FilePaste' );
        }
    
        Base.inherits( RuntimeClent, {
            constructor: FilePaste,
    
            init: function() {
                var me = this;
    
                me.connectRuntime( me.options, function() {
                    me.exec('init');
                });
            },
    
            destroy: function() {
                this.exec('destroy');
                this.disconnectRuntime();
                this.off();
            }
        });
    
        Mediator.installTo( FilePaste.prototype );
    
        return FilePaste;
    });

    /**
     * @fileOverview Image
     */
    define( 'lib/image', [
        'base',
        'runtime/client',
        'lib/blob'
    ], function( Base, RuntimeClient, Blob ) {
        var $ = Base.$;
    
        // ��������
        function Image( opts ) {
            this.options = $.extend({}, Image.options, opts );
            RuntimeClient.call( this, 'Image' );
    
            this.on( 'load', function() {
                this._info = this.exec('info');
                this._meta = this.exec('meta');
            });
        }
    
        // Ĭ��ѡ�
        Image.options = {
    
            // Ĭ�ϵ�ͼƬ��������
            quality: 90,
    
            // �Ƿ�ü�
            crop: false,
    
            // �Ƿ���ͷ����Ϣ
            preserveHeaders: true,
    
            // �Ƿ�����Ŵ�
            allowMagnify: true
        };
    
        // �̳�RuntimeClient.
        Base.inherits( RuntimeClient, {
            constructor: Image,
    
            info: function( val ) {
    
                // setter
                if ( val ) {
                    this._info = val;
                    return this;
                }
    
                // getter
                return this._info;
            },
    
            meta: function( val ) {
    
                // setter
                if ( val ) {
                    this._meta = val;
                    return this;
                }
    
                // getter
                return this._meta;
            },
    
            loadFromBlob: function( blob ) {
                var me = this,
                    ruid = blob.getRuid();
    
                this.connectRuntime( ruid, function() {
                    me.exec( 'init', me.options );
                    me.exec( 'loadFromBlob', blob );
                });
            },
    
            resize: function() {
                var args = Base.slice( arguments );
                return this.exec.apply( this, [ 'resize' ].concat( args ) );
            },
    
            getAsDataUrl: function( type ) {
                return this.exec( 'getAsDataUrl', type );
            },
    
            getAsBlob: function( type ) {
                var blob = this.exec( 'getAsBlob', type );
    
                return new Blob( this.getRuid(), blob );
            }
        });
    
        return Image;
    });

    /**
     * @fileOverview Transport
     */
    define( 'lib/transport', [
        'base',
        'runtime/client',
        'mediator'
    ], function( Base, RuntimeClient, Mediator ) {
    
        var $ = Base.$;
    
        function Transport( opts ) {
            var me = this;
    
            opts = me.options = $.extend( true, {}, Transport.options, opts || {} );
            RuntimeClient.call( this, 'Transport' );
    
            this._blob = null;
            this._formData = opts.formData || {};
            this._headers = opts.headers || {};
    
            this.on( 'progress', this._timeout );
            this.on( 'load error', function() {
                me.trigger( 'progress', 1 );
                clearTimeout( me._timer );
            });
        }
    
        Transport.options = {
            server: '',
            method: 'POST',
    
            // ����ʱ���Ƿ�����Я��cookie, ֻ��html5 runtime����Ч
            withCredentials: false,
            fileVar: 'file',
            timeout: 2 * 60 * 1000,    // 2����
            formData: {},
            headers: {},
            sendAsBinary: false
        };
    
        $.extend( Transport.prototype, {
    
            // ���Blob, ֻ�����һ�Σ����һ����Ч��
            appendBlob: function( key, blob, filename ) {
                var me = this,
                    opts = me.options;
    
                if ( me.getRuid() ) {
                    me.disconnectRuntime();
                }
    
                // ���ӵ�blob������ͬһ��runtime.
                me.connectRuntime( blob.ruid, function() {
                    me.exec('init');
                });
    
                me._blob = blob;
                opts.fileVar = key || opts.fileVar;
                opts.filename = filename || opts.filename;
            },
    
            // ��������ֶ�
            append: function( key, value ) {
                if ( typeof key === 'object' ) {
                    $.extend( this._formData, key );
                } else {
                    this._formData[ key ] = value;
                }
            },
    
            setRequestHeader: function( key, value ) {
                if ( typeof key === 'object' ) {
                    $.extend( this._headers, key );
                } else {
                    this._headers[ key ] = value;
                }
            },
    
            send: function( method ) {
                this.exec( 'send', method );
                this._timeout();
            },
    
            abort: function() {
                clearTimeout( this._timer );
                return this.exec('abort');
            },
    
            destroy: function() {
                this.trigger('destroy');
                this.off();
                this.exec('destroy');
                this.disconnectRuntime();
            },
    
            getResponse: function() {
                return this.exec('getResponse');
            },
    
            getResponseAsJson: function() {
                return this.exec('getResponseAsJson');
            },
    
            getStatus: function() {
                return this.exec('getStatus');
            },
    
            _timeout: function() {
                var me = this,
                    duration = me.options.timeout;
    
                if ( !duration ) {
                    return;
                }
    
                clearTimeout( me._timer );
                me._timer = setTimeout(function() {
                    me.abort();
                    me.trigger( 'error', 'timeout' );
                }, duration );
            }
    
        });
    
        // ��Transport�߱��¼����ܡ�
        Mediator.installTo( Transport.prototype );
    
        return Transport;
    });

    /**
     * @fileOverview �ļ�����
     */
    define( 'queue', [
        'base',
        'mediator',
        'file'
    ], function( Base, Mediator, WUFile ) {
    
        var $ = Base.$,
            STATUS = WUFile.Status;
    
        /**
         * �ļ�����, �����洢����״̬�е��ļ���
         * @class Queue
         * @extends Mediator
         */
        function Queue() {
    
            /**
             * ͳ���ļ�����
             * * `numOfQueue` �����е��ļ�����
             * * `numOfSuccess` �ϴ��ɹ����ļ���
             * * `numOfCancel` ���Ƴ����ļ���
             * * `numOfProgress` �����ϴ��е��ļ���
             * * `numOfUploadFailed` �ϴ�������ļ�����
             * * `numOfInvalid` ��Ч���ļ�����
             * @property {Object} stats
             */
            this.stats = {
                numOfQueue: 0,
                numOfSuccess: 0,
                numOfCancel: 0,
                numOfProgress: 0,
                numOfUploadFailed: 0,
                numOfInvalid: 0
            };
    
            // �ϴ����У��������ȴ��ϴ����ļ�
            this._queue = [];
    
            // �洢�����ļ�
            this._map = {};
        }
    
        $.extend( Queue.prototype, {
    
            /**
             * �����ļ�����Զ���β��
             *
             * @method append
             * @param  {File} file   �ļ�����
             */
            append: function( file ) {
                this._queue.push( file );
                this._fileAdded( file );
                return this;
            },
    
            /**
             * �����ļ�����Զ���ͷ��
             *
             * @method prepend
             * @param  {File} file   �ļ�����
             */
            prepend: function( file ) {
                this._queue.unshift( file );
                this._fileAdded( file );
                return this;
            },
    
            /**
             * ��ȡ�ļ�����
             *
             * @method getFile
             * @param  {String} fileId   �ļ�ID
             * @return {File}
             */
            getFile: function( fileId ) {
                if ( typeof fileId !== 'string' ) {
                    return fileId;
                }
                return this._map[ fileId ];
            },
    
            /**
             * �Ӷ�����ȡ��һ��ָ��״̬���ļ���
             * @grammar fetch( status ) => File
             * @method fetch
             * @param {String} status [�ļ�״ֵ̬](#WebUploader:File:File.Status)
             * @return {File} [File](#WebUploader:File)
             */
            fetch: function( status ) {
                var len = this._queue.length,
                    i, file;
    
                status = status || STATUS.QUEUED;
    
                for ( i = 0; i < len; i++ ) {
                    file = this._queue[ i ];
    
                    if ( status === file.getStatus() ) {
                        return file;
                    }
                }
    
                return null;
            },
    
            /**
             * ��ȡָ�����͵��ļ��б�, �б���ÿһ����ԱΪ[File](#WebUploader:File)����
             * @grammar getFiles( [status1[, status2 ...]] ) => Array
             * @method getFiles
             * @param {String} [status] [�ļ�״ֵ̬](#WebUploader:File:File.Status)
             */
            getFiles: function() {
                var sts = [].slice.call( arguments, 0 ),
                    ret = [],
                    i = 0,
                    len = this._queue.length,
                    file;
    
                for ( ; i < len; i++ ) {
                    file = this._queue[ i ];
    
                    if ( sts.length && !~$.inArray( file.getStatus(), sts ) ) {
                        continue;
                    }
    
                    ret.push( file );
                }
    
                return ret;
            },
    
            _fileAdded: function( file ) {
                var me = this,
                    existing = this._map[ file.id ];
    
                if ( !existing ) {
                    this._map[ file.id ] = file;
    
                    file.on( 'statuschange', function( cur, pre ) {
                        me._onFileStatusChange( cur, pre );
                    });
                }
    
                file.setStatus( STATUS.QUEUED );
            },
    
            _onFileStatusChange: function( curStatus, preStatus ) {
                var stats = this.stats;
    
                switch ( preStatus ) {
                    case STATUS.PROGRESS:
                        stats.numOfProgress--;
                        break;
    
                    case STATUS.QUEUED:
                        stats.numOfQueue --;
                        break;
    
                    case STATUS.ERROR:
                        stats.numOfUploadFailed--;
                        break;
    
                    case STATUS.INVALID:
                        stats.numOfInvalid--;
                        break;
                }
    
                switch ( curStatus ) {
                    case STATUS.QUEUED:
                        stats.numOfQueue++;
                        break;
    
                    case STATUS.PROGRESS:
                        stats.numOfProgress++;
                        break;
    
                    case STATUS.ERROR:
                        stats.numOfUploadFailed++;
                        break;
    
                    case STATUS.COMPLETE:
                        stats.numOfSuccess++;
                        break;
    
                    case STATUS.CANCELLED:
                        stats.numOfCancel++;
                        break;
    
                    case STATUS.INVALID:
                        stats.numOfInvalid++;
                        break;
                }
            }
    
        });
    
        Mediator.installTo( Queue.prototype );
    
        return Queue;
    });

    /**
     * @fileOverview Runtime������������Runtime��ѡ��, ����
     */
    define( 'runtime/compbase', function() {
    
        function CompBase( owner, runtime ) {
    
            this.owner = owner;
            this.options = owner.options;
    
            this.getRuntime = function() {
                return runtime;
            };
    
            this.getRuid = function() {
                return runtime.uid;
            };
    
            this.trigger = function() {
                return owner.trigger.apply( owner, arguments );
            };
        }
    
        return CompBase;
    });

    /**
     * @fileOverview FlashRuntime
     */
    define( 'runtime/flash/runtime', [
        'base',
        'runtime/runtime',
        'runtime/compbase'
    ], function( Base, Runtime, CompBase ) {
    
        var $ = Base.$,
            type = 'flash',
            components = {};
    
    
        function getFlashVersion() {
            var version;
    
            try {
                version = navigator.plugins[ 'Shockwave Flash' ];
                version = version.description;
            } catch ( ex ) {
                try {
                    version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                            .GetVariable('$version');
                } catch ( ex2 ) {
                    version = '0.0';
                }
            }
            version = version.match( /\d+/g );
            return parseFloat( version[ 0 ] + '.' + version[ 1 ], 10 );
        }
    
        function FlashRuntime() {
            var pool = {},
                clients = {},
                destory = this.destory,
                me = this,
                jsreciver = Base.guid('webuploader_');
    
            Runtime.apply( me, arguments );
            me.type = type;
    
    
            // ��������ĵ����ߣ�ʵ������RuntimeClient
            me.exec = function( comp, fn/*, args...*/ ) {
                var client = this,
                    uid = client.uid,
                    args = Base.slice( arguments, 2 ),
                    instance;
    
                clients[ uid ] = client;
    
                if ( components[ comp ] ) {
                    if ( !pool[ uid ] ) {
                        pool[ uid ] = new components[ comp ]( client, me );
                    }
    
                    instance = pool[ uid ];
    
                    if ( instance[ fn ] ) {
                        return instance[ fn ].apply( instance, args );
                    }
                }
    
                return me.flashExec.apply( client, arguments );
            };
    
            function hander( evt, obj ) {
                var type = evt.type || evt,
                    parts, uid;
    
                parts = type.split('::');
                uid = parts[ 0 ];
                type = parts[ 1 ];
    
                // console.log.apply( console, arguments );
    
                if ( type === 'Ready' && uid === me.uid ) {
                    me.trigger('ready');
                } else if ( clients[ uid ] ) {
                    clients[ uid ].trigger( type.toLowerCase(), evt, obj );
                }
    
                // Base.log( evt, obj );
            }
    
            // flash�Ľ�������
            window[ jsreciver ] = function() {
                var args = arguments;
    
                // Ϊ���ܲ���õ���
                setTimeout(function() {
                    hander.apply( null, args );
                }, 1 );
            };
    
            this.jsreciver = jsreciver;
    
            this.destory = function() {
                // @todo ɾ�������е�����ʵ��
                return destory && destory.apply( this, arguments );
            };
    
            this.flashExec = function( comp, fn ) {
                var flash = me.getFlash(),
                    args = Base.slice( arguments, 2 );
    
                return flash.exec( this.uid, comp, fn, args );
            };
    
            // @todo
        }
    
        Base.inherits( Runtime, {
            constructor: FlashRuntime,
    
            init: function() {
                var container = this.getContainer(),
                    opts = this.options,
                    html;
    
                // if not the minimal height, shims are not initialized
                // in older browsers (e.g FF3.6, IE6,7,8, Safari 4.0,5.0, etc)
                container.css({
                    position: 'absolute',
                    top: '-8px',
                    left: '-8px',
                    width: '9px',
                    height: '9px',
                    overflow: 'hidden'
                });
    
                // insert flash object
                html = '<object id="' + this.uid + '" type="application/' +
                        'x-shockwave-flash" data="' +  opts.swf + '" ';
    
                if ( Base.isIE ) {
                    html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                }
    
                html += 'width="100%" height="100%" style="outline:0">'  +
                    '<param name="movie" value="' + opts.swf + '" />' +
                    '<param name="flashvars" value="uid=' + this.uid +
                    '&jsreciver=' + this.jsreciver + '" />' +
                    '<param name="wmode" value="transparent" />' +
                    '<param name="allowscriptaccess" value="always" />' +
                '</object>';
    
                container.html( html );
            },
    
            getFlash: function() {
                if ( this._flash ) {
                    return this._flash;
                }
    
                this._flash = $( '#' + this.uid ).get( 0 );
                return this._flash;
            }
    
        });
    
        FlashRuntime.register = function( name, component ) {
            component = components[ name ] = Base.inherits( CompBase, $.extend({
    
                // @todo fix this later
                flashExec: function() {
                    var owner = this.owner,
                        runtime = this.getRuntime();
    
                    return runtime.flashExec.apply( owner, arguments );
                }
            }, component ) );
    
            return component;
        };
    
        if ( getFlashVersion() >= 11.3 ) {
            Runtime.addRuntime( type, FlashRuntime );
        }
    
        return FlashRuntime;
    });

    /**
     * @fileOverview FilePicker
     */
    define( 'runtime/flash/filepicker', [
        'base',
        'runtime/flash/runtime'
    ], function( Base, FlashRuntime ) {
        var $ = Base.$;
    
        return FlashRuntime.register( 'FilePicker', {
            init: function( opts ) {
                var copy = $.extend({}, opts );
    
                delete copy.button;
                delete copy.container;
    
                this.flashExec( 'FilePicker', 'init', copy );
            },
    
            destroy: function() {
                // todo
            }
        });
    });

    /**
     * @fileOverview ͼƬѹ��
     */
    define( 'runtime/flash/image', [
        'runtime/flash/runtime'
    ], function( FlashRuntime ) {
    
        return FlashRuntime.register( 'Image', {
            // init: function( options ) {
            //     var owner = this.owner;
    
            //     this.flashExec( 'Image', 'init', options );
            //     owner.on( 'load', function() {
            //         debugger;
            //     });
            // },
    
            loadFromBlob: function( blob ) {
                var owner = this.owner;
    
                owner.info() && this.flashExec( 'Image', 'info', owner.info() );
                owner.meta() && this.flashExec( 'Image', 'meta', owner.meta() );
    
                this.flashExec( 'Image', 'loadFromBlob', blob.uid );
            }
        });
    });

    /**
     * @fileOverview  Transport flashʵ��
     */
    define( 'runtime/flash/transport', [
        'base',
        'runtime/flash/runtime',
        'runtime/client'
    ], function( Base, FlashRuntime, RuntimeClient ) {
    
        return FlashRuntime.register( 'Transport', {
            init: function() {
                this._status = 0;
                this._response = null;
                this._responseJson = null;
            },
    
            send: function() {
                var owner = this.owner,
                    opts = this.options,
                    xhr = this._initAjax(),
                    blob = owner._blob,
                    server = opts.server,
                    binary;
    
                xhr.connectRuntime( blob.ruid );
    
                if ( opts.sendAsBinary ) {
                    server += (/\?/.test( server ) ? '&' : '?') +
                            $.param( owner._formData );
    
                    binary = blob.uid;
                } else {
                    $.each( owner._formData, function( k, v ) {
                        xhr.exec( 'append', k, v );
                    });
    
                    xhr.exec( 'appendBlob', opts.fileVar, blob.uid,
                            opts.filename || owner._formData.name || '' );
                }
    
                this._setRequestHeader( xhr, opts.headers );
                xhr.exec( 'send', {
                    method: opts.method,
                    url: server
                }, binary );
            },
    
            getStatus: function() {
                return this._status;
            },
    
            getResponse: function() {
                return this._response;
            },
    
            getResponseAsJson: function() {
                return this._responseJson;
            },
    
            abort: function() {
                var xhr = this._xhr;
    
                if ( xhr ) {
                    xhr.exec('abort');
                    xhr.destroy();
                    this._xhr = xhr = null;
                }
            },
    
            destroy: function() {
                this.abort();
            },
    
            _initAjax: function() {
                var me = this,
                    xhr = new RuntimeClient('XMLHttpRequest');
    
                xhr.on( 'uploadprogress progress', function( e ) {
                    return me.trigger( 'progress', e.loaded / e.total );
                });
    
                xhr.on( 'load', function() {
                    var status = xhr.exec('getStatus');
    
                    xhr.off();
                    me._xhr = null;
    
                    if ( status === 200 ) {
                        me._response = xhr.exec('getResponse');
                        me._responseJson = xhr.exec('getResponseAsJson');
                        return me.trigger('load');
                    }
    
                    me._status = status;
                    xhr.destroy();
                    xhr = null;
    
                    return me.trigger( 'error', 'http' );
                });
    
                xhr.on( 'error', function() {
                    xhr.off();
                    me._xhr = null;
                    me.trigger( 'error', 'http' );
                });
    
                me._xhr = xhr;
                return xhr;
            },
    
            _setRequestHeader: function( xhr, headers ) {
                $.each( headers, function( key, val ) {
                    xhr.exec( 'setRequestHeader', key, val );
                });
            }
        });
    });

    /**
     * @fileOverview DragAndDrop Widget��
     */
    define( 'widgets/filednd', [
        'base',
        'uploader',
        'lib/dnd',
        'widgets/widget'
    ], function( Base, Uploader, Dnd ) {
    
        Uploader.options.dnd = '';
    
        /**
         * @property {Selector} [dnd=undefined]  ָ��Drag And Drop��ק�������������ָ������������
         * @namespace options
         * @for Uploader
         */
        return Uploader.register({
            init: function( opts ) {
    
                if ( !opts.dnd ||
                        this.request('predict-runtime-type') !== 'html5' ) {
                    return;
                }
    
                var me = this,
                    deferred = Base.Deferred(),
                    options = $.extend({}, {
                        container: opts.dnd,
                        accept: opts.accept
                    }),
                    dnd;
    
                dnd = new Dnd( options );
    
                dnd.once( 'ready', deferred.resolve );
                dnd.on( 'drop', function( files ) {
                    me.request( 'add-file', [ files ]);
                });
                dnd.init();
    
                return deferred.promise();
            }
        });
    });

    /**
     * @fileOverview ������ࡣ
     */
    define( 'widgets/filepaste', [
        'base',
        'uploader',
        'lib/filepaste',
        'widgets/widget'
    ], function( Base, Uploader, FilePaste ) {
    
        /**
         * @property {Selector} [paste=undefined]  ָ������paste�¼��������������ָ���������ô˹��ܡ��˹���Ϊͨ��ճ������ӽ�����ͼƬ����������Ϊ`document.body`.
         * @namespace options
         * @for Uploader
         */
        return Uploader.register({
            init: function( opts ) {
    
                if ( !opts.paste ||
                        this.request('predict-runtime-type') !== 'html5' ) {
                    return;
                }
    
                var me = this,
                    deferred = Base.Deferred(),
                    options = $.extend({}, {
                        container: opts.paste,
                        accept: opts.accept
                    }),
                    paste;
    
                paste = new FilePaste( options );
    
                paste.once( 'ready', deferred.resolve );
                paste.on( 'paste', function( files ) {
                    me.owner.request( 'add-file', [ files ]);
                });
                paste.init();
    
                return deferred.promise();
            }
        });
    });

    /**
     * @fileOverview ͼƬ����, ����Ԥ��ͼƬ���ϴ�ǰѹ��ͼƬ
     */
    define( 'widgets/image', [
        'base',
        'uploader',
        'lib/image',
        'widgets/widget'
    ], function( Base, Uploader, Image ) {
    
        var $ = Base.$,
            throttle;
    
        // ����Ҫ������ļ���С��������һ�β��ܴ���̫�࣬�Ῠ��
        throttle = (function( max ) {
            var occupied = 0,
                waiting = [],
                tick = function() {
                    var item;
    
                    while ( waiting.length && occupied < max ) {
                        item = waiting.shift();
                        occupied += item[ 0 ];
                        item[ 1 ]();
                    }
                };
    
            return function( emiter, size, cb ) {
                waiting.push([ size, cb ]);
                emiter.once( 'destroy', function() {
                    occupied -= size;
                    setTimeout( tick, 1 );
                });
                setTimeout( tick, 1 );
            };
        })( 5 * 1024 * 1024 );
    
        $.extend( Uploader.options, {
    
            /**
             * @property {Object} [thumb]
             * @namespace options
             * @for Uploader
             * @description ������������ͼ��ѡ�
             *
             * Ĭ��Ϊ��
             *
             * ```javascript
             * {
             *     width: 110,
             *     height: 110,
             *
             *     // ͼƬ������ֻ��typeΪ`image/jpeg`��ʱ�����Ч��
             *     quality: 70,
             *
             *     // �Ƿ�����Ŵ������Ҫ����Сͼ��ʱ��ʧ�棬��ѡ��Ӧ������Ϊfalse.
             *     allowMagnify: true,
             *
             *     // �Ƿ�����ü���
             *     crop: true,
             *
             *     // �Ƿ���ͷ��meta��Ϣ��
             *     preserveHeaders: false,
             *
             *     // Ϊ�յĻ�����ԭ��ͼƬ��ʽ��
             *     // ����ǿ��ת����ָ�������͡�
             *     type: 'image/jpeg'
             * }
             * ```
             */
            thumb: {
                width: 110,
                height: 110,
                quality: 70,
                allowMagnify: true,
                crop: true,
                preserveHeaders: false,
    
                // Ϊ�յĻ�����ԭ��ͼƬ��ʽ��
                // ����ǿ��ת����ָ�������͡�
                type: 'image/jpeg'
            },
    
            /**
             * @property {Object} [compress]
             * @namespace options
             * @for Uploader
             * @description ����ѹ����ͼƬ��ѡ������ѡ��Ϊ`false`, ��ͼƬ���ϴ�ǰ������ѹ����
             *
             * Ĭ��Ϊ��
             *
             * ```javascript
             * {
             *     width: 1600,
             *     height: 1600,
             *
             *     // ͼƬ������ֻ��typeΪ`image/jpeg`��ʱ�����Ч��
             *     quality: 90,
             *
             *     // �Ƿ�����Ŵ������Ҫ����Сͼ��ʱ��ʧ�棬��ѡ��Ӧ������Ϊfalse.
             *     allowMagnify: false,
             *
             *     // �Ƿ�����ü���
             *     crop: false,
             *
             *     // �Ƿ���ͷ��meta��Ϣ��
             *     preserveHeaders: true
             * }
             * ```
             */
            compress: {
                width: 1600,
                height: 1600,
                quality: 90,
                allowMagnify: false,
                crop: false,
                preserveHeaders: true
            }
        });
    
        return Uploader.register({
            'make-thumb': 'makeThumb',
            'before-send-file': 'compressImage'
        }, {
    
    
            /**
             * ��������ͼ���˹���Ϊ�첽��������Ҫ����`callback`��
             * ͨ�������ͼƬ����������ô˷���������Ԥ��ͼ����ǿ����Ч����
             *
             * `callback`�п��Խ��յ�����������
             * * ��һ��Ϊerror�������������ͼ�д��󣬴�error��Ϊ�档
             * * �ڶ���Ϊret, ����ͼ��Data URLֵ��
             *
             * **ע��**
             * Date URL��IE6/7�в�֧�֣����Բ��õ��ô˷����ˣ�ֱ����ʾһ���ݲ�֧��Ԥ��ͼƬ���ˡ�
             *
             *
             * @method makeThumb
             * @grammar makeThumb( file, callback ) => undefined
             * @grammar makeThumb( file, callback, width, height ) => undefined
             * @for Uploader
             * @example
             *
             * uploader.on( 'fileQueued', function( file ) {
             *     var $li = ...;
             *
             *     uploader.makeThumb( file, function( error, ret ) {
             *         if ( error ) {
             *             $li.text('Ԥ������');
             *         } else {
             *             $li.append('<img alt="" src="' + ret + '" />');
             *         }
             *     });
             *
             * });
             */
            makeThumb: function( file, cb, width, height ) {
                var opts, image;
    
                file = this.request( 'get-file', file );
    
                // ֻԤ��ͼƬ��ʽ��
                if ( !file.type.match( /^image/ ) ) {
                    cb( true );
                    return;
                }
    
                opts = $.extend({}, this.options.thumb );
    
                // ����������object.
                if ( $.isPlainObject( width ) ) {
                    opts = $.extend( opts, width );
                    width = null;
                }
    
                width = width || opts.width;
                height = height || opts.height;
    
                image = new Image( opts );
    
                image.once( 'load', function() {
                    file._info = file._info || image.info();
                    file._meta = file._meta || image.meta();
                    image.resize( width, height );
                });
    
                image.once( 'complete', function() {
                    cb( false, image.getAsDataUrl( opts.type ) );
                    image.destroy();
                });
    
                image.once( 'error', function() {
                    cb( true );
                    image.destroy();
                });
    
                throttle( image, file.source.size, function() {
                    file._info && image.info( file._info );
                    file._meta && image.meta( file._meta );
                    image.loadFromBlob( file.source );
                });
            },
    
            compressImage: function( file ) {
                var opts = this.options.compress || this.options.resize,
                    compressSize = opts && opts.compressSize || 300 * 1024,
                    image, deferred;
    
                file = this.request( 'get-file', file );
    
                // ֻԤ��ͼƬ��ʽ��
                if ( !opts || !~'image/jpeg,image/jpg'.indexOf( file.type ) ||
                        file.size < compressSize ||
                        file._compressed ) {
                    return;
                }
    
                opts = $.extend({}, opts );
                deferred = Base.Deferred();
    
                image = new Image( opts );
    
                deferred.always(function() {
                    image.destroy();
                    image = null;
                });
                image.once( 'error', deferred.reject );
                image.once( 'load', function() {
                    file._info = file._info || image.info();
                    file._meta = file._meta || image.meta();
                    image.resize( opts.width, opts.height );
                });
    
                image.once( 'complete', function() {
                    var blob, size;
    
                    blob = image.getAsBlob( opts.type );
                    size = file.size;
    
                    // ���ѹ���󣬱�ԭ����������ѹ����ġ�
                    if ( blob.size < size ) {
                        // file.source.destroy && file.source.destroy();
                        file.source = blob;
                        file.size = blob.size;
    
                        file.trigger( 'resize', blob.size, size );
                    }
    
                    // ��ǣ������ظ�ѹ����
                    file._compressed = true;
                    deferred.resolve( true );
                });
    
                file._info && image.info( file._info );
                file._meta && image.meta( file._meta );
    
                image.loadFromBlob( file.source );
                return deferred.promise();
            }
        });
    });

    /**
     * @fileOverview ����
     */
    define( 'widgets/queue', [
        'base',
        'uploader',
        'queue',
        'file',
        'widgets/widget'
    ], function( Base, Uploader, Queue, WUFile ) {
    
        var $ = Base.$,
            rExt = /\.\w+$/,
            Status = WUFile.Status;
    
        return Uploader.register({
            'add-file': 'addFiles',
            'get-file': 'getFile',
            'fetch-file': 'fetchFile',
            'get-stats': 'getStats',
            'get-files': 'getFiles',
            'remove-file': 'removeFile',
            'retry': 'retry'
        }, {
    
            init: function( opts ) {
                var len, i, item, arr, accept;
    
                if ( $.isPlainObject( opts.accept ) ) {
                    opts.accept = [ opts.accept ];
                }
    
                // accept�е�������ƥ������
                if ( opts.accept ) {
                    arr = [];
    
                    for ( i = 0, len = opts.accept.length; i < len; i++ ) {
                        item = opts.accept[ i ].extensions;
                        item && arr.push( item );
                    }
    
                    if ( arr.length ) {
                        accept = '\\.' + arr.join(',')
                                .replace( /,/g, '$|\\.' )
                                .replace( /\*/g, '.*' ) + '$';
                    }
    
                    this.accept = new RegExp( accept, 'i' );
                }
    
                this.queue = new Queue();
                this.stats = this.queue.stats;
            },
    
            /**
             * @event beforeFileQueued
             * @param {File} file File����
             * @description ���ļ����������֮ǰ���������¼���handler����ֵΪ`false`������ļ����ᱻ��ӽ�����С�
             * @for  Uploader
             */
    
            /**
             * @event fileQueued
             * @param {File} file File����
             * @description ���ļ�����������Ժ󴥷���
             * @for  Uploader
             */
    
    
            _addFile: function( file ) {
                var me = this;
    
                if ( !file || file.size < 6 || me.accept &&
    
                        // ����������к�׺��������׺����������
                        rExt.exec( file.name ) && !me.accept.test( file.name ) ) {
                    return;
                }
    
                if ( !(file instanceof WUFile) ) {
                    file = new WUFile( file );
                }
    
                if ( !me.owner.trigger( 'beforeFileQueued', file ) ) {
                    return;
                }
    
                me.queue.append( file );
                me.owner.trigger( 'fileQueued', file );
                return file;
            },
    
            getFile: function( fileId ) {
                return this.queue.getFile( fileId );
            },
    
            /**
             * @event filesQueued
             * @param {File} files ���飬����ΪԭʼFile(lib/File������
             * @description ��һ���ļ���ӽ������Ժ󴥷���
             * @for  Uploader
             */
            addFiles: function( files ) {
                var me = this;
    
                if ( !files.length ) {
                    files = [ files ];
                }
    
                files = $.map( files, function( file ) {
                    return me._addFile( file );
                });
    
                me.owner.trigger( 'filesQueued', files );
    
                if ( me.options.auto ) {
                    me.request('start-upload');
                }
            },
    
            getStats: function() {
                return this.stats;
            },
    
            /**
             * @event fileDequeued
             * @param {File} file File����
             * @description ���ļ����Ƴ����к󴥷���
             * @for  Uploader
             */
    
            /**
             * @method removeFile
             * @grammar removeFile( file ) => undefined
             * @grammar removeFile( id ) => undefined
             * @param {File|id} file File�������File�����id
             * @description �Ƴ�ĳһ�ļ���
             * @for  Uploader
             * @example
             *
             * $li.on('click', '.remove-this', function() {
             *     uploader.removeFile( file );
             * })
             */
            removeFile: function( file ) {
                var me = this;
    
                file = file.id ? file : me.queue.getFile( file );
    
                file.setStatus( Status.CANCELLED );
                me.owner.trigger( 'fileDequeued', file );
            },
    
            /**
             * @method getFiles
             * @grammar getFiles() => Array
             * @grammar getFiles( status1, status2, status... ) => Array
             * @description ����ָ��״̬���ļ����ϣ�������������������״̬���ļ���
             * @for  Uploader
             * @example
             * console.log( uploader.getFiles() );    // => all files
             * console.log( uploader.getFiles('error') )    // => all error files.
             */
            getFiles: function() {
                return this.queue.getFiles.apply( this.queue, arguments );
            },
    
            fetchFile: function() {
                return this.queue.fetch.apply( this.queue, arguments );
            },
    
            /**
             * @method retry
             * @grammar retry() => undefined
             * @grammar retry( file ) => undefined
             * @description �����ϴ�������ָ���ļ������ߴӳ�����ļ���ʼ�����ϴ���
             * @for  Uploader
             * @example
             * function retry() {
             *     uploader.retry();
             * }
             */
            retry: function( file, noForceStart ) {
                var me = this,
                    files, i, len;
    
                if ( file ) {
                    file = file.id ? file : me.queue.getFile( file );
                    file.setStatus( Status.QUEUED );
                    noForceStart || me.request('start-upload');
                    return;
                }
    
                files = me.queue.getFiles( Status.ERROR );
                i = 0;
                len = files.length;
    
                for ( ; i < len; i++ ) {
                    file = files[ i ];
                    file.setStatus( Status.QUEUED );
                }
    
                me.request('start-upload');
            }
        });
    
    });

    /**
     * @fileOverview ��ӻ�ȡRuntime�����Ϣ�ķ�����
     */
    define( 'widgets/runtime', [
        'uploader',
        'runtime/runtime',
        'widgets/widget'
    ], function( Uploader, Runtime ) {
    
        Uploader.support = function() {
            return Runtime.hasRuntime.apply( Runtime, arguments );
        };
    
        return Uploader.register({
            'predict-runtime-type': 'predictRuntmeType'
        }, {
    
            init: function() {
                if ( !this.predictRuntmeType() ) {
                    throw Error('Runtime Error');
                }
            },
    
            /**
             * Ԥ��Uploader�������ĸ�`Runtime`
             * @grammar predictRuntmeType() => String
             * @method predictRuntmeType
             * @for  Uploader
             */
            predictRuntmeType: function() {
                var orders = this.options.runtimeOrder || Runtime.orders,
                    type = this.type,
                    i, len;
    
                if ( !type ) {
                    orders = orders.split( /\s*,\s*/g );
    
                    for ( i = 0, len = orders.length; i < len; i++ ) {
                        if ( Runtime.hasRuntime( orders[ i ] ) ) {
                            this.type = type = orders[ i ];
                            break;
                        }
                    }
                }
    
                return type;
            }
        });
    });

    /**
     * @fileOverview �����ļ��ϴ���ء�
     */
    define( 'widgets/upload', [
        'base',
        'uploader',
        'file',
        'lib/transport',
        'widgets/widget'
    ], function( Base, Uploader, WUFile, Transport ) {
    
        var $ = Base.$,
            isPromise = Base.isPromise,
            Status = WUFile.Status;
    
        // ���Ĭ��������
        $.extend( Uploader.options, {
    
    
            /**
             * @property {Boolean} [prepareNextFile=false]
             * @namespace options
             * @for Uploader
             * @description �Ƿ��������ļ�����ʱ��ǰ����һ���ļ�׼���á�
             * ����һ���ļ���׼�������ȽϺ�ʱ������ͼƬѹ����md5���л���
             * �������ǰ�ڵ�ǰ�ļ������ڴ������Խ�ʡ�����ʱ��
             */
            prepareNextFile: false,
    
            /**
             * @property {Boolean} [chunked=false]
             * @namespace options
             * @for Uploader
             * @description �Ƿ�Ҫ��Ƭ������ļ��ϴ���
             */
            chunked: false,
    
            /**
             * @property {Boolean} [chunkSize=5242880]
             * @namespace options
             * @for Uploader
             * @description ���Ҫ��Ƭ���ֶ��һƬ�� Ĭ�ϴ�СΪ5M.
             */
            chunkSize: 5 * 1024 * 1024,
    
            /**
             * @property {Boolean} [chunkRetry=2]
             * @namespace options
             * @for Uploader
             * @description ���ĳ����Ƭ��������������������Զ��ش����ٴΣ�
             */
            chunkRetry: 2,
    
            /**
             * @property {Boolean} [threads=3]
             * @namespace options
             * @for Uploader
             * @description �ϴ�������������ͬʱ����ϴ���������
             */
            threads: 3,
    
    
            /**
             * @property {Object} [formdata]
             * @namespace options
             * @for Uploader
             * @description �ļ��ϴ�����Ĳ�����ÿ�η��Ͷ��ᷢ�ʹ˶����еĲ�����
             */
            formdata: null
        });
    
        // �����ļ���Ƭ��
        function CuteFile( file, chunkSize ) {
            var pending = [],
                blob = file.source,
                total = blob.size,
                chunks = chunkSize ? Math.ceil( total / chunkSize ) : 1,
                start = 0,
                index = 0,
                len;
    
            while ( index < chunks ) {
                len = Math.min( chunkSize, total - start );
                pending.push({
                    file: file,
                    start: start,
                    end: start + len,
                    total: total,
                    chunks: chunks,
                    chunk: index++
                });
                start += len;
            }
    
            file.blocks = pending.concat();
            file.remaning = pending.length;
    
            return {
                file: file,
    
                has: function() {
                    return !!pending.length;
                },
    
                fetch: function() {
                    return pending.shift();
                }
            };
        }
    
        Uploader.register({
            'start-upload': 'start',
            'stop-upload': 'stop',
            'skip-file': 'skipFile',
            'is-in-progress': 'isInProgress'
        }, {
    
            init: function() {
                var owner = this.owner;
    
                this.runing = false;
    
                // ��¼��ǰ���ڴ������ݣ���threads���
                this.pool = [];
    
                // ���漴���ϴ����ļ���
                this.pending = [];
    
                // ���ٻ��ж��ٷ�Ƭû������ϴ���
                this.remaning = 0;
                this.__tick = Base.bindFn( this._tick, this );
    
                owner.on( 'uploadComplete', function( file ) {
                    // ��������ȡ���ˡ�
                    file.blocks && $.each( file.blocks, function( _, v ) {
                        v.transport && (v.transport.abort(), v.transport.destroy());
                        delete v.transport;
                    });
    
                    delete file.blocks;
                    delete file.remaning;
                });
            },
    
            /**
             * @event startUpload
             * @description ����ʼ�ϴ�����ʱ������
             * @for  Uploader
             */
    
            /**
             * ��ʼ�ϴ����˷������Դӳ�ʼ״̬���ÿ�ʼ�ϴ����̣�Ҳ���Դ���ͣ״̬���ã������ϴ����̡�
             * @grammar upload() => undefined
             * @method upload
             * @for  Uploader
             */
            start: function() {
                var me = this;
    
                // �Ƴ�invalid���ļ�
                $.each( me.request( 'get-files', Status.INVALID ), function() {
                    me.request( 'remove-file', this );
                });
    
                if ( me.runing ) {
                    return;
                }
    
                me.runing = true;
    
                // �������ͣ�ģ�������
                $.each( me.pool, function( _, v ) {
                    var file = v.file;
    
                    if ( file.getStatus() === Status.INTERRUPT ) {
                        file.setStatus( Status.PROGRESS );
                        me._trigged = false;
                        v.transport && v.transport.send();
                    }
                });
    
                me._trigged = false;
                me.owner.trigger('startUpload');
                Base.nextTick( me.__tick );
            },
    
            /**
             * @event stopUpload
             * @description ����ʼ�ϴ�������ͣʱ������
             * @for  Uploader
             */
    
            /**
             * ��ͣ�ϴ�����һ������Ϊ�Ƿ��ж��ϴ���ǰ�����ϴ����ļ���
             * @grammar stop() => undefined
             * @grammar stop( true ) => undefined
             * @method stop
             * @for  Uploader
             */
            stop: function( interrupt ) {
                var me = this;
    
                if ( me.runing === false ) {
                    return;
                }
    
                me.runing = false;
    
                interrupt && $.each( me.pool, function( _, v ) {
                    v.transport && v.transport.abort();
                    v.file.setStatus( Status.INTERRUPT );
                });
    
                me.owner.trigger('stopUpload');
            },
    
            /**
             * �ж�`Uplaode`r�Ƿ������ϴ��С�
             * @grammar isInProgress() => Boolean
             * @method isInProgress
             * @for  Uploader
             */
            isInProgress: function() {
                return !!this.runing;
            },
    
            getStats: function() {
                return this.request('get-stats');
            },
    
            /**
             * ����һ���ļ��ϴ���ֱ�ӱ��ָ���ļ�Ϊ���ϴ�״̬��
             * @grammar skipFile( file ) => undefined
             * @method skipFile
             * @for  Uploader
             */
            skipFile: function( file, status ) {
                file = this.request( 'get-file', file );
    
                file.setStatus( status || Status.COMPLETE );
                file.skipped = true;
    
                // ��������ϴ���
                file.blocks && $.each( file.blocks, function( _, v ) {
                    var _tr = v.transport;
    
                    if ( _tr ) {
                        _tr.abort();
                        _tr.destroy();
                        delete v.transport;
                    }
                });
    
                this.owner.trigger( 'uploadSkip', file );
            },
    
            /**
             * @event uploadFinished
             * @description ���ļ��ϴ�����ʱ������
             * @for  Uploader
             */
            _tick: function() {
                var me = this,
                    opts = me.options,
                    fn, val;
    
                // ��һ��promise��û�н�������ȴ���ɺ���ִ�С�
                if ( me._promise ) {
                    return me._promise.always( me.__tick );
                }
    
                // ����λ�ã��һ����ļ�Ҫ����Ļ���
                if ( me.pool.length < opts.threads && (val = me._nextBlock()) ) {
                    me._trigged = false;
    
                    fn = function( val ) {
                        me._promise = null;
    
                        // �п�����reject�����ģ�����Ҫ���val�����͡�
                        val && val.file && me._startSend( val );
                        Base.nextTick( me.__tick );
                    };
    
                    me._promise = isPromise( val ) ? val.always( fn ) : fn( val );
    
                // û��Ҫ�ϴ����ˣ���û�����ڴ�����ˡ�
                } else if ( !me.remaning && !me.getStats().numOfQueue ) {
                    me.runing = false;
    
                    me._trigged || Base.nextTick(function() {
                        me.owner.trigger('uploadFinished');
                    });
                    me._trigged = true;
                }
            },
    
            _nextBlock: function() {
                var me = this,
                    act = me._act,
                    opts = me.options,
                    next, done;
    
                // �����ǰ�ļ�����û����Ҫ����ģ���ֱ�ӷ���ʣ�µġ�
                if ( act && act.has() &&
                        act.file.getStatus() === Status.PROGRESS ) {
    
                    // �Ƿ���ǰ׼����һ���ļ�
                    if ( opts.prepareNextFile && !me.pending.length ) {
                        me._prepareNextFile();
                    }
    
                    return act.fetch();
    
                // ��������������У���׼����һ���ļ������ȴ���ɺ󷵻��¸���Ƭ��
                } else if ( me.runing ) {
    
                    // ����������У���ֱ���ڻ�����ȡ��û����ȥqueue��ȡ��
                    if ( !me.pending.length && me.getStats().numOfQueue ) {
                        me._prepareNextFile();
                    }
    
                    next = me.pending.shift();
                    done = function( file ) {
                        if ( !file ) {
                            return null;
                        }
    
                        act = CuteFile( file, opts.chunked ? opts.chunkSize : 0 );
                        me._act = act;
                        return act.fetch();
                    };
    
                    // �ļ����ܻ���prepare�У�Ҳ�п����Ѿ���ȫ׼�����ˡ�
                    return isPromise( next ) ? next.then( done ) : done( next );
                }
            },
    
    
            /**
             * @event uploadStart
             * @param {File} file File����
             * @description ĳ���ļ���ʼ�ϴ�ǰ������
             * @for  Uploader
             */
            _prepareNextFile: function() {
                var me = this,
                    file = me.request('fetch-file'),
                    pending = me.pending,
                    promise;
    
                if ( file ) {
    
                    promise = me.request( 'before-send-file', file, function() {
    
                        // �п����ļ���skip���ˡ��ļ���skip����״̬�Ӷ�����Queued.
                        if ( file.getStatus() === Status.QUEUED ) {
                            me.owner.trigger( 'uploadStart', file );
                            file.setStatus( Status.PROGRESS );
                            return file;
                        }
    
                        return me._finishFile( file );
                    });
    
                    // �������pending�У����滻���ļ�����
                    promise.done(function() {
                        var idx = $.inArray( promise, pending );
    
                        ~idx && pending.splice( idx, 1, file );
                    });
    
                    // befeore-send-file�Ĺ��Ӿ��д�������
                    promise.fail(function( reason ) {
                        file.setStatus( Status.ERROR, reason );
                        me.owner.trigger( 'uploadError', file, reason );
                        me.owner.trigger( 'uploadComplete', file );
                    });
    
                    pending.push( promise );
                }
            },
    
            // �ó�λ���ˣ�������������Ƭ��ʼ�ϴ�
            _popBlock: function( block ) {
                var idx = $.inArray( block, this.pool );
    
                this.pool.splice( idx, 1 );
                block.file.remaning--;
                this.remaning--;
            },
    
            // ��ʼ�ϴ������Ա����������promise��reject�ˣ����ʾ�����˷�Ƭ��
            _startSend: function( block ) {
                var me = this,
                    file = block.file,
                    promise;
    
                me.pool.push( block );
                me.remaning++;
    
                // ���û�з�Ƭ����ֱ��ʹ��ԭʼ�ġ�
                // ���ᶪʧcontent-type��Ϣ��
                block.blob = block.chunks === 1 ? file.source :
                        file.source.slice( block.start, block.end );
    
                // hook, ÿ����Ƭ����֮ǰ����Ҫ��Щ�첽�����顣
                promise = me.request( 'before-send', block, function() {
    
                    // �п����ļ��Ѿ��ϴ������ˣ����Բ���Ҫ�ٴ����ˡ�
                    if ( file.getStatus() === Status.PROGRESS ) {
                        me._doSend( block );
                    } else {
                        me._popBlock( block );
                        Base.nextTick( me.__tick );
                    }
                });
    
                // ���Ϊfail�ˣ��������˷�Ƭ��
                promise.fail(function() {
                    if ( file.remaning === 1 ) {
                        me._finishFile( file ).always(function() {
                            block.percentage = 1;
                            me._popBlock( block );
                            me.owner.trigger( 'uploadComplete', file );
                            Base.nextTick( me.__tick );
                        });
                    } else {
                        block.percentage = 1;
                        me._popBlock( block );
                        Base.nextTick( me.__tick );
                    }
                });
            },
    
    
            /**
             * @event uploadBeforeSend
             * @param {Object} object
             * @param {Object} data Ĭ�ϵ��ϴ�������������չ�˶����������ϴ�������
             * @description �������ٷ���ǰ������
             * @for  Uploader
             */
    
            /**
             * @event uploadAccept
             * @param {Object} object
             * @param {Object} ret ����˵ķ������ݣ�json��ʽ���������˲���json��ʽ����ret._raw��ȡ���ݣ����н�����
             * @description ��ĳ���ļ��ϴ����������Ӧ�󣬻����ʹ��¼���ѯ�ʷ������Ӧ�Ƿ���Ч��������¼�handler����ֵΪ`false`, ����ļ�������`server`���͵�`uploadError`�¼���
             * @for  Uploader
             */
    
            /**
             * @event uploadProgress
             * @param {File} file File����
             * @param {Number} percentage �ϴ�����
             * @description �ϴ������д�����Я���ϴ����ȡ�
             * @for  Uploader
             */
    
    
            /**
             * @event uploadError
             * @param {File} file File����
             * @param {String} reason �����code
             * @description ���ļ��ϴ�����ʱ������
             * @for  Uploader
             */
    
            /**
             * @event uploadSuccess
             * @param {File} file File����
             * @description ���ļ��ϴ��ɹ�ʱ������
             * @for  Uploader
             */
    
            /**
             * @event uploadComplete
             * @param {File} [file] File����
             * @description ���ܳɹ�����ʧ�ܣ��ļ��ϴ����ʱ������
             * @for  Uploader
             */
    
            // ���ϴ�������
            _doSend: function( block ) {
                var me = this,
                    owner = me.owner,
                    opts = me.options,
                    file = block.file,
                    tr = new Transport( opts ),
                    data = $.extend({}, opts.formData ),
                    headers = $.extend({}, opts.headers );
    
                block.transport = tr;
    
                tr.on( 'destroy', function() {
                    delete block.transport;
                    me._popBlock( block );
                    Base.nextTick( me.__tick );
                });
    
                // �㲥�ϴ����ȡ����ļ�Ϊ��λ��
                tr.on( 'progress', function( percentage ) {
                    var totalPercent = 0,
                        uploaded = 0;
    
                    // ����û��abort����progress����ִ�н����ˡ�
                    // if ( !file.blocks ) {
                    //     return;
                    // }
    
                    totalPercent = block.percentage = percentage;
    
                    if ( block.chunks > 1 ) {    // �����ļ��������ٶȡ�
                        $.each( file.blocks, function( _, v ) {
                            uploaded += (v.percentage || 0) * (v.end - v.start);
                        });
    
                        totalPercent = uploaded / file.size;
                    }
    
                    owner.trigger( 'uploadProgress', file, totalPercent || 0 );
                });
    
                // �������ԣ�Ȼ��㲥�ļ��ϴ�����
                tr.on( 'error', function( type ) {
                    block.retried = block.retried || 0;
    
                    // �Զ�����
                    if ( block.chunks > 1 && ~'http,abort'.indexOf( type ) &&
                            block.retried < opts.chunkRetry ) {
    
                        block.retried++;
                        tr.send();
    
                    } else {
                        file.setStatus( Status.ERROR, type );
                        owner.trigger( 'uploadError', file, type );
                        owner.trigger( 'uploadComplete', file );
                    }
                });
    
                // �ϴ��ɹ�
                tr.on( 'load', function() {
                    var ret = tr.getResponseAsJson() || {},
                        reject, fn;
    
                    ret._raw = tr.getResponse();
                    fn = function( value ) {
                        reject = value;
                    };
    
                    // �������Ӧ�ˣ�������ɹ��ˣ�ѯ���Ƿ���Ӧ��ȷ��
                    if ( !owner.trigger( 'uploadAccept', block, ret, fn ) ) {
                        reject = reject || 'server';
                    }
    
                    // �����Ԥ�ڣ�ת���ϴ�����
                    if ( reject ) {
                        tr.trigger( 'error', reject );
                        return;
                    }
    
                    // ȫ���ϴ���ɡ�
                    if ( file.remaning === 1 ) {
                        me._finishFile( file, ret );
                    } else {
                        tr.destroy();
                    }
                });
    
                // ����Ĭ�ϵ��ϴ��ֶΡ�
                data = $.extend( data, {
                    id: file.id,
                    name: file.name,
                    type: file.type,
                    lastModifiedDate: file.lastModifiedDate,
                    size: file.size
                });
    
                block.chunks > 1 && $.extend( data, {
                    chunks: block.chunks,
                    chunk: block.chunk
                });
    
                // �ڷ���֮���������ֶ�ʲô�ġ�����
                // ���Ĭ�ϵ��ֶβ���ʹ�ã�����ͨ���������¼�����չ
                owner.trigger( 'uploadBeforeSend', block, data, headers );
    
                // ��ʼ���͡�
                tr.appendBlob( opts.fileVal, block.blob, file.name );
                tr.append( data );
                tr.setRequestHeader( headers );
                tr.send();
            },
    
            // ����ϴ���
            _finishFile: function( file, ret, hds ) {
                var owner = this.owner;
    
                return owner
                        .request( 'after-send-file', arguments, function() {
                            file.setStatus( Status.COMPLETE );
                            owner.trigger( 'uploadSuccess', file, ret, hds );
                        })
                        .fail(function( reason ) {
    
                            // ����ⲿ�Ѿ����Ϊinvalidʲô�ģ����ٸ�״̬��
                            if ( file.getStatus() === Status.PROGRESS ) {
                                file.setStatus( Status.ERROR, reason );
                            }
    
                            owner.trigger( 'uploadError', file, reason );
                        })
                        .always(function() {
                            owner.trigger( 'uploadComplete', file );
                        });
            }
    
        });
    });

    /**
     * @fileOverview ������֤�������ļ��ܴ�С�Ƿ񳬳������ļ��Ƿ񳬳����ļ��Ƿ��ظ���
     */
    
    define( 'widgets/validator', [
        'base',
        'uploader',
        'file',
        'widgets/widget'
    ], function( Base, Uploader, WUFile ) {
    
        var $ = Base.$,
            validators = {},
            api;
    
        // ��¶�������api
        api = {
    
            // �����֤��
            addValidator: function( type, cb ) {
                validators[ type ] = cb;
            },
    
            // �Ƴ���֤��
            removeValidator: function( type ) {
                delete validators[ type ];
            }
        };
    
        // ��Uploader��ʼ����ʱ������Validators�ĳ�ʼ��
        Uploader.register({
            init: function() {
                var me = this;
                $.each( validators, function() {
                    this.call( me.owner );
                });
            }
        });
    
        /**
         * @property {int} [fileNumLimit=undefined]
         * @namespace options
         * @for Uploader
         * @description ��֤�ļ�������, ���������������С�
         */
        api.addValidator( 'fileNumLimit', function() {
            var uploader = this,
                opts = uploader.options,
                count = 0,
                max = opts.fileNumLimit >> 0,
                flag = true;
    
            if ( !max ) {
                return;
            }
    
            uploader.on( 'beforeFileQueued', function() {
    
                if ( count >= max && flag ) {
                    flag = false;
                    this.trigger( 'error', 'Q_EXCEED_NUM_LIMIT', max );
                    setTimeout(function() {
                        flag = true;
                    }, 1 );
                }
    
                return count >= max ? false : true;
            });
    
            uploader.on( 'fileQueued', function() {
                count++;
            });
    
            uploader.on( 'fileDequeued', function() {
                count--;
            });
        });
    
    
        /**
         * @property {int} [fileSizeLimit=undefined]
         * @namespace options
         * @for Uploader
         * @description ��֤�ļ��ܴ�С�Ƿ񳬳�����, ���������������С�
         */
        api.addValidator( 'fileSizeLimit', function() {
            var uploader = this,
                opts = uploader.options,
                count = 0,
                max = opts.fileSizeLimit >> 0,
                flag = true;
    
            if ( !max ) {
                return;
            }
    
            uploader.on( 'beforeFileQueued', function( file ) {
                var invalid = count + file.size > max;
    
                if ( invalid && flag ) {
                    flag = false;
                    this.trigger( 'error', 'Q_EXCEED_SIZE_LIMIT', max );
                    setTimeout(function() {
                        flag = true;
                    }, 1 );
                }
    
                return invalid ? false : true;
            });
    
            uploader.on( 'fileQueued', function( file ) {
                count += file.size;
            });
    
            uploader.on( 'fileDequeued', function( file ) {
                count -= file.size;
            });
        });
    
        /**
         * @property {int} [fileSingleSizeLimit=undefined]
         * @namespace options
         * @for Uploader
         * @description ��֤�����ļ���С�Ƿ񳬳�����, ���������������С�
         */
        api.addValidator( 'fileSingleSizeLimit', function() {
            var uploader = this,
                opts = uploader.options,
                max = opts.fileSingleSizeLimit;
    
            if ( !max ) {
                return;
            }
    
            uploader.on( 'fileQueued', function( file ) {
                if ( file.size > max ) {
                    file.setStatus( WUFile.Status.INVALID, 'exceed_size' );
                }
            });
        });
    
        /**
         * @property {int} [duplicate=undefined]
         * @namespace options
         * @for Uploader
         * @description ȥ�أ� �����ļ����֡��ļ���С������޸�ʱ��������hash Key.
         */
        api.addValidator( 'duplicate', function() {
            var uploader = this,
                opts = uploader.options,
                mapping = {};
    
            if ( opts.duplicate ) {
                return;
            }
    
            function hashString( str ) {
                var hash = 0,
                    i = 0,
                    len = str.length,
                    _char;
    
                for ( ; i < len; i++ ) {
                    _char = str.charCodeAt( i );
                    hash = _char + (hash << 6) + (hash << 16) - hash;
                }
    
                return hash;
            }
    
            uploader.on( 'beforeFileQueued', function( file ) {
                var hash = hashString( file.name + file.size +
                        file.lastModifiedDate );
    
                // �Ѿ��ظ���
                if ( mapping[ hash ] ) {
                    return false;
                }
            });
    
            uploader.on( 'fileQueued', function( file ) {
                var hash = hashString( file.name + file.size +
                        file.lastModifiedDate );
    
                mapping[ hash ] = true;
            });
    
            uploader.on( 'fileDequeued', function( file ) {
                var hash = hashString( file.name + file.size +
                        file.lastModifiedDate );
    
                delete mapping[ hash ];
            });
        });
    
        return api;
    });

    /**
     * @file ��¶�������ⲿʹ�á�
     * ���ļ�Ҳֻ���ڰ�webupload�ϲ���һ���ļ�ʹ�õ�ʱ��Ż����롣
     *
     * ������modules����·��idsװ���ɶ���
     */
    (function( modules ) {
        var
            // ����д��ĸ��д��
            ucFirst = function( str ) {
                return str && (str.charAt( 0 ).toUpperCase() + str.substr( 1 ));
            },
    
            // ��¶��ȥ��key
            exportName = 'WebUploader',
            exports = modules.base,
            key, host, parts, part, last, origin;
    
        for ( key in modules ) {
            host = exports;
    
            if ( !modules.hasOwnProperty( key ) ) {
                continue;
            }
    
            parts = key.split('/');
            last = ucFirst( parts.pop() );
    
            while( (part = ucFirst( parts.shift() )) ) {
                host[ part ] = host[ part ] || {};
                host = host[ part ];
            }
    
            host[ last ] = modules[ key ];
        }
    
        if ( typeof module === 'object' && typeof module.exports === 'object' ) {
            module.exports = exports;
        } else if ( window.define && window.define.amd ) {
            window.define( function() { return exports; } );
        } else {
            origin = window[ exportName ];
            window[ exportName ] = exports;
            window[ exportName ].noConflict = function() {
                window[ exportName ] = origin;
            };
        }
    })( internalAmd.modules );
    
})( this );