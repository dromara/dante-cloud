var App = function () {

	var currentPage = ''; // current page
	var collapsed = false; //sidebar collapsed
	var is_mobile = false; //is screen mobile?
	var is_mini_menu = false; //is mini-menu activated
	var is_fixed_header = false; //is fixed header activated
	var responsiveFunctions = []; //responsive function holder
	
	/*-----------------------------------------------------------------------------------*/
	/*	Runs callback functions set by App.addResponsiveFunction()
	/*-----------------------------------------------------------------------------------*/
    var runResponsiveFunctions = function () {
        // reinitialize other subscribed elements
        for (var i in responsiveFunctions) {
            var each = responsiveFunctions[i];
            each.call();
        }
    }
	/*-----------------------------------------------------------------------------------*/
	/*	To get the correct viewport width based on  http://andylangton.co.uk/articles/javascript/get-viewport-size-javascript/
	/*-----------------------------------------------------------------------------------*/
    var getViewPort = function () {
        var e = window, a = 'inner';
        if (!('innerWidth' in window)) {
            a = 'client';
            e = document.documentElement || document.body;
        }
        return {
            width: e[a + 'Width'],
            height: e[a + 'Height']
        }
    }
	/*-----------------------------------------------------------------------------------*/
	/*	Check layout size
	/*-----------------------------------------------------------------------------------*/
	var checkLayout = function() {
		//Check if sidebar has mini-menu
		is_mini_menu = $('#sidebar').hasClass('mini-menu');
		//Check if fixed header is activated
		is_fixed_header = $('#header').hasClass('navbar-fixed-top');
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Sidebar & Main Content size match
	/*-----------------------------------------------------------------------------------*/
	var handleSidebarAndContentHeight = function () {
        var content = $('#content');
        var sidebar = $('#sidebar');
        var body = $('body');
        var height;

        if (body.hasClass('sidebar-fixed')) {
            height = $(window).height() - $('#header').height() + 1;
        } else {
            height = sidebar.height() + 20;
        }
        if (height >= content.height()) {
            content.attr('style', 'min-height:' + height + 'px !important');
        }
    }
	/*-----------------------------------------------------------------------------------*/
	/*	Sidebar
	/*-----------------------------------------------------------------------------------*/
	var handleSidebar = function () {
	jQuery('.sidebar-menu .has-sub > a').click(function () {
            var last = jQuery('.has-sub.open', $('.sidebar-menu'));
            last.removeClass("open");
            jQuery('.arrow', last).removeClass("open");
            jQuery('.sub', last).slideUp(200);
            
			var thisElement = $(this);
			var slideOffeset = -200;
            var slideSpeed = 200;
			
            var sub = jQuery(this).next();
            if (sub.is(":visible")) {
                jQuery('.arrow', jQuery(this)).removeClass("open");
                jQuery(this).parent().removeClass("open");
				sub.slideUp(slideSpeed, function () {
					if ($('#sidebar').hasClass('sidebar-fixed') == false) {
						App.scrollTo(thisElement, slideOffeset);
					}
					handleSidebarAndContentHeight();
                });
            } else {
                jQuery('.arrow', jQuery(this)).addClass("open");
                jQuery(this).parent().addClass("open");
                sub.slideDown(slideSpeed, function () {
					if ($('#sidebar').hasClass('sidebar-fixed') == false) {
						App.scrollTo(thisElement, slideOffeset);
					}
					handleSidebarAndContentHeight();
                });
            }
        });
		
	// Handle sub-sub menus
	jQuery('.sidebar-menu .has-sub .sub .has-sub-sub > a').click(function () {
            var last = jQuery('.has-sub-sub.open', $('.sidebar-menu'));
            last.removeClass("open");
            jQuery('.arrow', last).removeClass("open");
            jQuery('.sub', last).slideUp(200);
                
            var sub = jQuery(this).next();
            if (sub.is(":visible")) {
                jQuery('.arrow', jQuery(this)).removeClass("open");
                jQuery(this).parent().removeClass("open");
                sub.slideUp(200);
            } else {
                jQuery('.arrow', jQuery(this)).addClass("open");
                jQuery(this).parent().addClass("open");
                sub.slideDown(200);
            }
        });
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	Collapse Sidebar Programatically
	/*-----------------------------------------------------------------------------------*/
	var collapseSidebar = function () {
		var iconElem = document.getElementById("sidebar-collapse").querySelector('[class*="fa-"]');
		var iconLeft = iconElem.getAttribute("data-icon1");
		var iconRight = iconElem.getAttribute("data-icon2");
		/* For Navbar */
		jQuery('.navbar-brand').addClass("mini-menu");
		/* For sidebar */
		jQuery('#sidebar').addClass("mini-menu");
		jQuery('#main-content').addClass("margin-left-50");
		jQuery('.sidebar-collapse i').removeClass(iconLeft);
		jQuery('.sidebar-collapse i').addClass(iconRight);
		/* Remove placeholder from Search Bar */
		jQuery('.search').attr('placeholder', '');
		collapsed = true;
		/* Set a cookie so that mini-sidebar persists */
		$.cookie('mini_sidebar', '1');
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Responsive Sidebar Collapse
	/*-----------------------------------------------------------------------------------*/
	var responsiveSidebar = function () {
		//Handle sidebar collapse on screen width
		var width = $(window).width();
		if ( width < 768 ) {
			is_mobile = true;
			//Hide the sidebar completely
			jQuery('#main-content').addClass("margin-left-0");
		}
		else {
			is_mobile = false;
			//Show the sidebar completely
			jQuery('#main-content').removeClass("margin-left-0");
			var menu = $('.sidebar');
			if (menu.parent('.slimScrollDiv').size() === 1) { // destroy existing instance before resizing
				menu.slimScroll({
					destroy: true
				});
				menu.removeAttr('style');
				$('#sidebar').removeAttr('style');
			}
		}
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Sidebar Collapse
	/*-----------------------------------------------------------------------------------*/
	var handleSidebarCollapse = function () {
		var viewport = getViewPort();
        if ($.cookie('mini_sidebar') === '1') {
			/* For Navbar */
			jQuery('.navbar-brand').addClass("mini-menu");
			/* For sidebar */
			jQuery('#sidebar').addClass("mini-menu");
			jQuery('#main-content').addClass("margin-left-50");
			collapsed = true;
        }
		//Handle sidebar collapse on user interaction
		jQuery('.sidebar-collapse').click(function () {
			//Handle mobile sidebar toggle
			if(is_mobile && !(is_mini_menu)){
				//If sidebar is collapsed
				if(collapsed){
					jQuery('body').removeClass("slidebar");
					jQuery('.sidebar').removeClass("sidebar-fixed");
					//Add fixed top nav if exists
					if(is_fixed_header) {
						jQuery('#header').addClass("navbar-fixed-top");
						jQuery('#main-content').addClass("margin-top-100");
					}
					collapsed = false;
					$.cookie('mini_sidebar', '0');
				}
				else {
					jQuery('body').addClass("slidebar");
					jQuery('.sidebar').addClass("sidebar-fixed");
					//Remove fixed top nav if exists
					if(is_fixed_header) {
						jQuery('#header').removeClass("navbar-fixed-top");
						jQuery('#main-content').removeClass("margin-top-100");
					}
					collapsed = true;
					$.cookie('mini_sidebar', '1');
					handleMobileSidebar();
				}
			}
			else { //Handle regular sidebar toggle
				var iconElem = document.getElementById("sidebar-collapse").querySelector('[class*="fa-"]');
				var iconLeft = iconElem.getAttribute("data-icon1");
				var iconRight = iconElem.getAttribute("data-icon2");
				//If sidebar is collapsed
				if(collapsed){
					/* For Navbar */
					jQuery('.navbar-brand').removeClass("mini-menu");
					/* For sidebar */
					jQuery('#sidebar').removeClass("mini-menu");
					jQuery('#main-content').removeClass("margin-left-50");
					jQuery('.sidebar-collapse i').removeClass(iconRight);
					jQuery('.sidebar-collapse i').addClass(iconLeft);
					/* Add placeholder from Search Bar */
					jQuery('.search').attr('placeholder', "Search");
					collapsed = false;
					$.cookie('mini_sidebar', '0');
				}
				else {
					/* For Navbar */
					jQuery('.navbar-brand').addClass("mini-menu");
					/* For sidebar */
					jQuery('#sidebar').addClass("mini-menu");
					jQuery('#main-content').addClass("margin-left-50");
					jQuery('.sidebar-collapse i').removeClass(iconLeft);
					jQuery('.sidebar-collapse i').addClass(iconRight);
					/* Remove placeholder from Search Bar */
					jQuery('.search').attr('placeholder', '');
					collapsed = true;
					$.cookie('mini_sidebar', '1');
				}
				$("#main-content").on('resize', function (e) {
					e.stopPropagation();
				});
			}
        });
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handle Fixed Sidebar on Mobile devices
	/*-----------------------------------------------------------------------------------*/
	var handleMobileSidebar = function () {
        var menu = $('.sidebar');
		if (menu.parent('.slimScrollDiv').size() === 1) { // destroy existing instance before updating the height
            menu.slimScroll({
                destroy: true
            });
            menu.removeAttr('style');
            $('#sidebar').removeAttr('style');
        }
        menu.slimScroll({
            size: '7px',
            color: '#a1b2bd',
            opacity: .3,
            height: "100%",
            allowPageScroll: false,
            disableFadeOut: false
        });
    }
	/*-----------------------------------------------------------------------------------*/
	/*	Handle Fixed Sidebar
	/*-----------------------------------------------------------------------------------*/
	var handleFixedSidebar = function () {
        var menu = $('.sidebar-menu');

        if (menu.parent('.slimScrollDiv').size() === 1) { // destroy existing instance before updating the height
            menu.slimScroll({
                destroy: true
            });
            menu.removeAttr('style');
            $('#sidebar').removeAttr('style');
        }

        if ($('.sidebar-fixed').size() === 0) {
            handleSidebarAndContentHeight();
            return;
        }

        var viewport = getViewPort();
        if (viewport.width >= 992) {
            var sidebarHeight = $(window).height() - $('#header').height() + 1;

            menu.slimScroll({
                size: '7px',
                color: '#a1b2bd',
                opacity: .3,
                height: sidebarHeight,
                allowPageScroll: false,
                disableFadeOut: false
            });
            handleSidebarAndContentHeight();
        }
    }
	/*-----------------------------------------------------------------------------------*/
	/*	Windows Resize function
	/*-----------------------------------------------------------------------------------*/
	jQuery(window).resize(function() {
		setTimeout(function () {
			checkLayout();
			handleSidebarAndContentHeight();
			responsiveSidebar();
			handleFixedSidebar();
			handleNavbarFixedTop();
			runResponsiveFunctions(); 
		}, 50); // wait 50ms until window resize finishes.
	});
	/*-----------------------------------------------------------------------------------*/
	/*	Date Range Picker
	/*-----------------------------------------------------------------------------------*/
	var handleDateTimePickers = function () {

        $('#reportrange').daterangepicker(
            {
               startDate: moment().subtract('days', 29),
               endDate: moment(),
               minDate: '01/01/2012',
               maxDate: '12/31/2014',
               dateLimit: { days: 60 },
               showDropdowns: true,
               showWeekNumbers: true,
               timePicker: false,
               timePickerIncrement: 1,
               timePicker12Hour: true,
               ranges: {
                  'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
                  'Last 30 Days': [moment().subtract('days', 29), moment()],
                  'This Month': [moment().startOf('month'), moment().endOf('month')]
               },
               opens: 'left',
               buttonClasses: ['btn btn-default'],
               applyClass: 'btn-small btn-primary',
               cancelClass: 'btn-small',
               format: 'MM/DD/YYYY',
               separator: ' to ',
               locale: {
                   applyLabel: 'Submit',
                   fromLabel: 'From',
                   toLabel: 'To',
                   customRangeLabel: 'Custom Range',
                   daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr','Sa'],
                   monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
                   firstDay: 1
               }
            },
            function(start, end) {
             console.log("Callback has been called!");
             $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
            }
         );
         //Set the initial state of the picker label
         $('#reportrange span').html('Custom');
    }
	
	/*-----------------------------------------------------------------------------------*/
	/*	Team View
	/*-----------------------------------------------------------------------------------*/
	var handleTeamView = function () {
		c();
		$(".team-status-toggle").click(function (y) {
            y.preventDefault();
            w(this);
            $(this).parent().toggleClass("open");
            var z = x(this);
            $(z).slideToggle(200, function () {
                $(this).toggleClass("open")
            })
        });
        $("body").click(function (z) {
            var y = z.target.className.split(" ");
            if ($.inArray("team-status", y) == -1 && $.inArray("team-status-toggle", y) == -1 && $(z.target).parents().index($(".team-status")) == -1 && $(z.target).parents(".team-status-toggle").length == 0) {
                w()
            }
        });
        $(".team-status #teamslider").each(function () {
            $(this).slimScrollHorizontal({
                width: "100%",
                alwaysVisible: true,
                color: "#fff",
                opacity: "0.5",
                size: "5px"
            })
        });
        var w = function (y) {
            $(".team-status").each(function () {
                var z = $(this);
                if (z.is(":visible")) {
                    var A = x(y);
                    if (A != ("#" + z.attr("id"))) {
                        $(this).slideUp(200, function () {
                            $(this).toggleClass("open");
                            $(".team-status-toggle").each(function () {
                                var B = x(this);
                                if (B == ("#" + z.attr("id"))) {
                                    $(this).parent().removeClass("open")
                                }
                            })
                        })
                    }
                }
            })
        };
        var x = function (y) {
            var z = $(y).data("teamStatus");
            if (typeof z == "undefined") {
                z = "#team-status"
            }
            return z
        }
	}
	var c = function () {
        $(".team-status").each(function () {
            var x = $(this);
            x.css("position", "absolute").css("margin-top", "-1000px").show();
            var w = 0;
            $("ul li", this).each(function () {
                w += $(this).outerWidth(true) + 15
            });
            x.css("position", "relative").css("margin-top", "0").hide();
            $("ul", this).width(w)
        })
    };
	
	/*-----------------------------------------------------------------------------------*/
	/*	Homepage tooltips
	/*-----------------------------------------------------------------------------------*/
	var handleHomePageTooltips = function () {
		//On Hover
		//Default tooltip (Top)
		$('.tip').tooltip();
		//Bottom tooltip
		$('.tip-bottom').tooltip({
			placement : 'bottom'
		});
		//Left tooltip
		$('.tip-left').tooltip({
			placement : 'left'
		});
		//Right tooltip
		$('.tip-right').tooltip({
			placement : 'right'
		});
		//On Focus
		//Default tooltip (Top)
		$('.tip-focus').tooltip({
			trigger: 'focus'
		});
	}
	
	/*-----------------------------------------------------------------------------------*/
	/* Box tools
	/*-----------------------------------------------------------------------------------*/
	var handleBoxTools = function () {
		//Collapse
		jQuery('.box .tools .collapse, .box .tools .expand').click(function () {
            var el = jQuery(this).parents(".box").children(".box-body");
            if (jQuery(this).hasClass("collapse")) {
				jQuery(this).removeClass("collapse").addClass("expand");
                var i = jQuery(this).children(".fa-chevron-up");
				i.removeClass("fa-chevron-up").addClass("fa-chevron-down");
                el.slideUp(200);
            } else {
				jQuery(this).removeClass("expand").addClass("collapse");
                var i = jQuery(this).children(".fa-chevron-down");
				i.removeClass("fa-chevron-down").addClass("fa-chevron-up");
                el.slideDown(200);
            }
        });
		
		/* Close */
		jQuery('.box .tools a.remove').click(function () {
            var removable = jQuery(this).parents(".box");
            if (removable.next().hasClass('box') || removable.prev().hasClass('box')) {
                jQuery(this).parents(".box").remove();
            } else {
                jQuery(this).parents(".box").parent().remove();
            }
        });
		
		/* Reload */
		jQuery('.box .tools a.reload').click(function () {
            var el = jQuery(this).parents(".box");
            App.blockUI(el);
            window.setTimeout(function () {
                App.unblockUI(el);
            }, 1000);
        });
	}
	/*-----------------------------------------------------------------------------------*/
	/*	SlimScroll
	/*-----------------------------------------------------------------------------------*/
	var handleSlimScrolls = function () {
        if (!jQuery().slimScroll) {
            return;
        }

        $('.scroller').each(function () {
            $(this).slimScroll({
                size: '7px',
                color: '#a1b2bd',
				height: $(this).attr("data-height"),
                alwaysVisible: ($(this).attr("data-always-visible") == "1" ? true : false),
                railVisible: ($(this).attr("data-rail-visible") == "1" ? true : false),
				railOpacity: 0.1,
                disableFadeOut: true
            });
        });
    }
	
	/*-----------------------------------------------------------------------------------*/
	/*	Bootbox alerts
	/*-----------------------------------------------------------------------------------*/
	var handleBootbox = function () {
		$(".basic-alert").click(function(){
			bootbox.alert("Hello World");
		});
		$(".confirm-dialog").click(function(){
			bootbox.confirm("Are you sure?", function(result){});
		});
		$(".multiple-buttons").click(function(){
			bootbox.dialog({
			message: "I am a custom dialog",
			title: "Custom title",
			buttons: {
				success: {
					label: "Success!",
					className: "btn-success",
					callback: function() {
					Example.show("great success");
					}
				},
				danger: {
					label: "Danger!",
					className: "btn-danger",
					callback: function() {
					Example.show("uh oh, look out!");
					}
				},
				main: {
					label: "Click ME!",
					className: "btn-primary",
					callback: function() {
					Example.show("Primary button");
					}
				}
			}
			});
		});
		$(".multiple-dialogs").click(function(){
			bootbox.alert("In 1 second a new modal will open");
			setTimeout(function() {
				bootbox.dialog({
			message: "Will you purchase this awesome theme",
			title: "Pop quiz",
			buttons: {
				success: {
					label: "Yes!",
					className: "btn-success",
					callback: function() {
						bootbox.alert("Congratulations! You made the right decision.", function(){
							$(".bootbox").modal("hide");
						});
					}
				},
				danger: {
					label: "No!",
					className: "btn-danger",
					callback: function() {
						bootbox.alert("Oops, we're sorry to hear that!", function(){
							$(".bootbox").modal("hide");
						});
						
					}
				},
				main: {
					label: "Click ME!",
					className: "btn-primary",
					callback: function() {
						bootbox.alert("Hello World", function(){
							$(".bootbox").modal("hide");
						});
					}
				}
			}
			});
			}, 1000);
		});
		$(".programmatic-close").click(function(){
			bootbox.alert("In 3 second this modal will close..");
			setTimeout(function() {
				$(".bootbox").modal("hide");
			}, 3000);
		});
		
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Popovers
	/*-----------------------------------------------------------------------------------*/
	var handlePopovers = function () {
		//Default (Right)
		$('.pop').popover();
		//Bottom 
		$('.pop-bottom').popover({
			placement : 'bottom'
		});
		//Left 
		$('.pop-left').popover({
			placement : 'left'
		});
		//Top 
		$('.pop-top').popover({
			placement : 'top'
		});
		//Trigger hover 
		$('.pop-hover').popover({
			trigger: 'hover'
		});
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	Hubspot messenger
	/*-----------------------------------------------------------------------------------*/
	var handleMessenger = function () {
		
		//Normal
		$("#normal").click(function(){
			var mytheme = $('input[name=theme]:checked').val();
			var mypos = $('input[name=position]:checked').val();
			//Set theme
			Messenger.options = {
				extraClasses: 'messenger-fixed '+mypos,
				theme: mytheme
			}
			//Call
			Messenger().post({
				message:"This is a normal notification!",
				showCloseButton: true
			});
		});
		//Interactive
		$("#interactive").click(function(){
			var mytheme = $('input[name=theme]:checked').val();
			var mypos = $('input[name=position]:checked').val();
			//Set theme
			Messenger.options = {
				extraClasses: 'messenger-fixed '+mypos,
				theme: mytheme
			}
			var msg;
			msg = Messenger().post({
			  message: 'Launching thermonuclear war...',
			  type: 'info',
			  actions: {
				cancel: {
				  label: 'cancel launch',
				  action: function() {
					return msg.update({
					  message: 'Thermonuclear war averted',
					  type: 'success',
					  showCloseButton: true,
					  actions: false
					});
				  }
				}
			  }
			});
		});
		//Timer
		$("#timer").click(function(){
			var mytheme = $('input[name=theme]:checked').val();
			var mypos = $('input[name=position]:checked').val();
			//Set theme
			Messenger.options = {
				extraClasses: 'messenger-fixed '+mypos,
				theme: mytheme
			}
			var i;
			i = 0;
			Messenger().run({
			  errorMessage: 'Error destroying alien planet',
			  successMessage: 'Alien planet destroyed!',
			  showCloseButton: true,
			  action: function(opts) {
				if (++i < 3) {
				  return opts.error({
					status: 500,
					readyState: 0,
					responseText: 0
				  });
				} else {
				  return opts.success();
				}
			  }
			});
		});
		//Prompts
		$("#prompts").click(function(){
			var mytheme = $('input[name=theme]:checked').val();
			var mypos = $('input[name=position]:checked').val();
			//Set theme
			Messenger.options = {
				extraClasses: 'messenger-fixed '+mypos,
				theme: mytheme
			}
			Messenger().run({
			  successMessage: 'Data saved.',
			  errorMessage: 'Error saving data',
			  progressMessage: 'Saving data',
			  showCloseButton: true,
			}, {
			  url: 'http://www.example.com/data'
			});
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Alerts
	/*-----------------------------------------------------------------------------------*/
	var handleAlerts = function () {
		$(".alert").alert();
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	Magic Suggest
	/*-----------------------------------------------------------------------------------*/
	var handleMagicSuggest = function () {
		var jsonData = [];
            var cities = 'New York,Los Angeles,Chicago,Houston,Paris,Marseille,Toulouse,Lyon,Bordeaux,Philadelphia,Phoenix,San Antonio,San Diego,Dallas,San Jose,Jacksonville'.split(',');
            for(var i=0;i<cities.length;i++) jsonData.push({id:i,name:cities[i],status:i%2?'Already Visited':'Planned for visit',coolness:Math.floor(Math.random() * 10) + 1});
            var ms1 = $('#ms1').magicSuggest({
                data: jsonData,
                sortOrder: 'name',
                value: [0],
                selectionPosition: 'right',
                groupBy: 'status',
                maxDropHeight: 200
            });
            var ms2 = $('#ms2').magicSuggest({
				width: '80%',
                data: jsonData
            });
            var ms3 = $('#ms3').magicSuggest({
                selectionPosition: 'bottom',
                renderer: function(city){
                    return '<div>' +
                            '<div style="font-family: Arial; font-weight: bold">' + city.name + '</div>' +
                            '<div><b>Cooooolness</b>: ' + city.coolness + '</div>' +
                           '</div>';
                },
                minChars: 1,
                selectionStacked: true,
                data: jsonData
            });
            var ms4 = $('#ms4').magicSuggest({
                data: [{id:1,label:'one'}, {id:2,label:'two'}, {id:3,label:'three'}],
                displayField: 'label',
                value: [1,3]
            });
            var ms5 = $('#ms5').magicSuggest({
                width: '80%',
                data: 'marilyn@monroe.com,mother@teresa.com,john@kennedy.com,martin@luther.com,nelson@mandela.com,winston@churchill.com,bill@gates.com,muhammad@ali.com,mahatma@gandhi.com,margaret@thatcher.com,charles@gaulle.com,christopher@colombus.com,george@orwell.com,charles@darwin.com,elvis@presley.com,albert@einstein.com,paul@mccartney.com,queen@elizabeth.com,queen@victoria.com,john@keynes.com,mikhail@gorbachev.com,jawaharlal@nehru.com,leonardo@vinci.com,louis@pasteur.com,leo@tolstoy.com,pablo@picasso.com,vincent@gogh.com,franklin@roosevelt.com,john@paul.com,neil@armstrong.com,thomas@edison.com,rosa@parks.com,aung@kyi.com,lyndon@johnson.com,ludwig@beethoven.com,oprah@winfrey.com,indira@gandhi.com,eva@peron.com,benazir@bhutto.com,desmond@tutu.com,dalai@lama.com,walt@disney.com,peter@sellers.com,barack@obama.com,malcolm@x.com,richard@branson.com,jesse@owens.com,ernest@hemingway.com,john@lennon.com,henry@ford.com,haile@selassie.com,joseph@stalin.com,lord@baden.com,michael@jordon.com,george@bush.com,osama@laden.com,fidel@castro.com,oscar@wilde.com,coco@chanel.com,amelia@earhart.com,adolf@hitler.com,mary@magdalene.com,alfred@hitchcock.com,michael@jackson.com,mata@hari.com,emmeline@pankhurst.com,ronald@reagan.com,lionel@messi.com,babe@ruth.com,bob@geldof.com,leon@trotsky.com,roger@federer.com,sigmund@freud.com,woodrow@wilson.com,mao@zedong.com,katherine@hepburn.com,audrey@hepburn.com,david@beckham.com,tiger@woods.com,usain@bolt.com,bill@cosby.com,carl@lewis.com,prince@charles.com,jacqueline@onassis.com,billie@holiday.com,virginia@woolf.com,billie@king.com,kylie@minogue.com,anne@frank.com,emile@zatopek.com,lech@walesa.com,christiano@ronaldo.com,yoko@ono.com,julie@andrews.com,florence@nightingale.com,marie@curie.com,stephen@hawking.com,tim@lee.com,lady@gaga.com,lance@armstrong.com,jon@stewart.com,scarlett@johansson.com,larry@page.com,sergey@brin.com,roman@abramovich.com,rupert@murdoch.com,al@gore.com,sacha@baron.com,george@clooney.com,paul@krugman.com,jimmy@wales.com'
            });
            var ms6 = $('#ms6').magicSuggest({
                // will fetch data from options
            });
            var ms7 = $('#ms7').magicSuggest({
                data: jsonData,
                resultAsString: true,
                maxSelection: 1,
                maxSelectionRenderer: function(){}
            })
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Timeago
	/*-----------------------------------------------------------------------------------*/
	var handleTimeAgo = function () {
		jQuery(document).ready(function() {
			var curr_time = moment().format('YYYY-MM-DD HH:mm');
			var yesterday = moment().subtract('days', 1).format('MMM D, YYYY');
			$("#curr-time").html(curr_time);
			$("#curr-time").attr('title', curr_time);
			$("#curr-time").attr('data-original-title', curr_time);
			$("#yesterday").html(yesterday);
			$("#yesterday").attr('title', yesterday);
			$("#yesterday").attr('data-original-title', yesterday);
		  jQuery("abbr.timeago").timeago();
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Init Timeago
	/*-----------------------------------------------------------------------------------*/
	var initTimeAgo = function () {
		jQuery("abbr.timeago").timeago();
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Date & Color Picker
	/*-----------------------------------------------------------------------------------*/
	var handleDateColorpicker = function () {
		$(".datepicker").datepicker();
		$(".inlinepicker").datepicker({
			inline: true,
			showOtherMonths: true
		});
		$(".datepicker-fullscreen").pickadate();
		$(".timepicker-fullscreen").pickatime();
		//Color picker
		$('.colorpicker').colorpicker();
		var a = $("#color-pickers")[0].style;
		$("#colorpicker-event").colorpicker().on("changeColor", function (b) {
			a.backgroundColor = b.color.toHex()
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Raty
	/*-----------------------------------------------------------------------------------*/
	var handleRaty = function () {
		$.fn.raty.defaults.path = 'js/jquery-raty/img';
		$('#score-demo').raty({ score: 3 });
		$('#number-demo').raty({ number: 10 });
		$('#readOnly-demo').raty({ readOnly: true, score: 2 });
		$('#halfShow-true-demo').raty({ score: 3.26 });
		$('#starHalf-demo').raty({
			path    : 'js/jquery-raty/img',
			half    : true,
			starOff : 'cookie-off.png',
			starOn  : 'cookie-on.png',

			starHalf: 'cookie-half.png'
		  });
		$('#star-off-and-star-on-demo').raty({
			path   : 'js/jquery-raty/img',
			starOff: 'off.png',
			starOn : 'on.png'
		});
		$('#cancel-off-and-cancel-on-demo').raty({
			path     : 'js/jquery-raty/img',
			cancel   : true,
			cancelOff: 'cancel-custom-off.png',
			cancelOn : 'cancel-custom-on.png',
			starOn   : 'star-on.png',
			starOff  : 'star-off.png'
		});
		$('#size-demo').raty({
			path     : 'js/jquery-raty/img',
			cancel   : true,
			cancelOff: 'cancel-off-big.png',
			cancelOn : 'cancel-on-big.png',
			half     : true,
			size     : 24,
			starHalf : 'star-half-big.png',
			starOff  : 'star-off-big.png',
			starOn   : 'star-on-big.png'
		});
		$('#target-div-demo').raty({
			cancel: true,
			target: '#target-div-hint'
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Stateful buttons
	/*-----------------------------------------------------------------------------------*/
	var handleStatefulButtons = function () {
		$(document).ready(function(){
		  $("#btn-load").on("click",function(){
			var a=$(this);
			a.button("loading");
			setTimeout(function(){
			  a.button("reset")}
					   ,1500)}
						   );
		  $("#btn-load-complete").on("click",function(){
			var a=$(this);
			a.button("loading");
			setTimeout(function(){
			  a.button("complete")}
					   ,1500)}
										 )}
						 );
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Toggle buttons
	/*-----------------------------------------------------------------------------------*/
	var handleToggle = function () {
	$('.radio1').on('switch-change', function () {
		$('.radio1').bootstrapSwitch('toggleRadioState');
		});
		// or
		$('.radio1').on('switch-change', function () {
		$('.radio1').bootstrapSwitch('toggleRadioStateAllowUncheck');
		});
		// or
		$('.radio1').on('switch-change', function () {
		$('.radio1').bootstrapSwitch('toggleRadioStateAllowUncheck', false);
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	jQuery UI Sliders
	/*-----------------------------------------------------------------------------------*/
	var handleSliders = function () {
	  function repositionTooltip( e, ui ){$
        var div = $(ui.handle).data("bs.tooltip").$tip[0];
        var pos = $.extend({}, $(ui.handle).offset(), { width: $(ui.handle).get(0).offsetWidth,
                                                        height: $(ui.handle).get(0).offsetHeight
                  });
        
        var actualWidth = div.offsetWidth;
        
        tp = {left: pos.left + pos.width / 2 - actualWidth / 2}            
        $(div).offset(tp);
        
        $(div).find(".tooltip-inner").text( ui.value );     
	}
    
	  $("#slider").slider({ value: 15, slide: repositionTooltip, stop: repositionTooltip });
	  $("#slider .ui-slider-handle:first").tooltip( {title: $("#slider").slider("value"), trigger: "manual"}).tooltip("show");
	  
	  $("#slider-default").slider();
	  
      $("#slider-range").slider({
        range:true,min:0,max:500,values:[75,300]
      });
	  
      $("#slider-range-min").slider({
        range:"min",value:37,min:1,max:700,slide:function(a,b){
          $("#slider-range-min-amount").text("$"+b.value)}
      });
	  
      $("#slider-range-max").slider({
        range:"max",min:1,max:700,value:300,slide:function(a,b){
          $("#slider-range-max-amount").text("$"+b.value)}
      });
	  
      $("#slider-vertical-multiple > span").each(function(){
        var a=parseInt($(this).text(),10);
        $(this).empty().slider({
          value:a,range:"min",animate:true,orientation:"vertical"}
                              )}
                                                );
      $("#slider-vertical-range-min").slider({
        range:"min",value:400,min:1,max:600,orientation:"vertical"
      });
	  $("#slider-horizontal-range-min").slider({
        range:"min",value:600,min:1,max:1000
      });
	}
	/*-----------------------------------------------------------------------------------*/
	/*	jQuery UI Progress
	/*-----------------------------------------------------------------------------------*/
	var handleProgress = function () {
		$(document).ready(function(){
			jQuery.fn.anim_progressbar = function (aOptions) {
				// def values
				var iCms = 1000;
				var iMms = 60 * iCms;
				var iHms = 3600 * iCms;
				var iDms = 24 * 3600 * iCms;

				// def options
				var aDefOpts = {
					start: new Date(), // now
					finish: new Date().setTime(new Date().getTime() + 60 * iCms), // now + 60 sec
					interval: 100
				}
				var aOpts = jQuery.extend(aDefOpts, aOptions);
				var vPb = this;

				// each progress bar
				return this.each(
					function() {
						var iDuration = aOpts.finish - aOpts.start;

						// calling original progressbar
						$(vPb).children('.pbar').progressbar();

						// looping process
						var vInterval = setInterval(
							function(){
								var iLeftMs = aOpts.finish - new Date(); // left time in MS
								var iElapsedMs = new Date() - aOpts.start, // elapsed time in MS
									iDays = parseInt(iLeftMs / iDms), // elapsed days
									iHours = parseInt((iLeftMs - (iDays * iDms)) / iHms), // elapsed hours
									iMin = parseInt((iLeftMs - (iDays * iDms) - (iHours * iHms)) / iMms), // elapsed minutes
									iSec = parseInt((iLeftMs - (iDays * iDms) - (iMin * iMms) - (iHours * iHms)) / iCms), // elapsed seconds
									iPerc = (iElapsedMs > 0) ? iElapsedMs / iDuration * 100 : 0; // percentages

								// display current positions and progress
								$(vPb).children('.percent').html('<b>'+iPerc.toFixed(1)+'%</b>');
								$(vPb).children('.elapsed').html(iDays+' day '+iHours+' hr : '+iMin+' min : '+iSec+' sec remaining</b>');
								$(vPb).children('.pbar').children('.ui-progressbar-value').css('width', iPerc+'%');

								// in case of Finish
								if (iPerc >= 100) {
									clearInterval(vInterval);
									$(vPb).children('.percent').html('<b>100%</b>');
									$(vPb).children('.elapsed').html('Completed');
								}
							} ,aOpts.interval
						);
					}
				);
			}

			// default mode
			$('#progress1').anim_progressbar();

			// from second #5 till 15
			var iNow = new Date().setTime(new Date().getTime() + 5 * 1000); // now plus 5 secs
			var iEnd = new Date().setTime(new Date().getTime() + 15 * 1000); // now plus 15 secs
			$('#progress2').anim_progressbar({start: iNow, finish: iEnd, interval: 100});

			// we will just set interval of updating to 1 sec
			$('#progress3').anim_progressbar({interval: 1000});
		});

	}
	/*-----------------------------------------------------------------------------------*/
	/*	jQuery Knob
	/*-----------------------------------------------------------------------------------*/
	var handleKnobs = function () {
		$(".knob").knob({
                    change : function (value) {
                        //console.log("change : " + value);
                    },
                    release : function (value) {
                        //console.log(this.$.attr('value'));
                        console.log("release : " + value);
                    },
                    cancel : function () {
                        console.log("cancel : ", this);
                    },
                    draw : function () {

                        // "tron" case
                        if(this.$.data('skin') == 'tron') {

                            var a = this.angle(this.cv)  // Angle
                                , sa = this.startAngle          // Previous start angle
                                , sat = this.startAngle         // Start angle
                                , ea                            // Previous end angle
                                , eat = sat + a                 // End angle
                                , r = 1;

                            this.g.lineWidth = this.lineWidth;

                            this.o.cursor
                                && (sat = eat - 0.3)
                                && (eat = eat + 0.3);

                            if (this.o.displayPrevious) {
                                ea = this.startAngle + this.angle(this.v);
                                this.o.cursor
                                    && (sa = ea - 0.3)
                                    && (ea = ea + 0.3);
                                this.g.beginPath();
                                this.g.strokeStyle = this.pColor;
                                this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, sa, ea, false);
                                this.g.stroke();
                            }

                            this.g.beginPath();
                            this.g.strokeStyle = r ? this.o.fgColor : this.fgColor ;
                            this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, sat, eat, false);
                            this.g.stroke();

                            this.g.lineWidth = 2;
                            this.g.beginPath();
                            this.g.strokeStyle = this.o.fgColor;
                            this.g.arc( this.xy, this.xy, this.radius - this.lineWidth + 1 + this.lineWidth * 2 / 3, 0, 2 * Math.PI, false);
                            this.g.stroke();

                            return false;
                        }
                    }
                });
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	Custom tabs
	/*-----------------------------------------------------------------------------------*/
	var handleCustomTabs = function () {
			var adjustMinHeight = function (y) {
				$(y).each(function () {
					var A = $($($(this).attr("href")));
					var z = $(this).parent().parent();
					if (z.height() > A.height()) {
						A.css("min-height", z.height())
					}
				})
			};
			$("body").on("click", '.nav.nav-tabs.tabs-left a[data-toggle="tab"], .nav.nav-tabs.tabs-right a[data-toggle="tab"]', function () {
				adjustMinHeight($(this))
			});
			adjustMinHeight('.nav.nav-tabs.tabs-left > li.active > a[data-toggle="tab"], .nav.nav-tabs.tabs-right > li.active > a[data-toggle="tab"]');
			if (location.hash) {
				var w = location.hash.substr(1);
				$('a[href="#' + w + '"]').click()
			}
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Fuel UX Tree
	/*-----------------------------------------------------------------------------------*/
	var handleTree = function () {
		$('#tree1').admin_tree({
			dataSource: treeDataSource ,
			multiSelect:true,
			loadingHTML:'<div class="tree-loading"><i class="fa fa-spinner fa-2x fa-spin"></i></div>',
			'open-icon' : 'fa-minus',
			'close-icon' : 'fa-plus',
			'selectable' : true,
			'selected-icon' : 'fa-check',
			'unselected-icon' : 'fa-times'
		});
		$('#tree3').admin_tree({
			dataSource: treeDataSource3 ,
			multiSelect:true,
			loadingHTML:'<div class="tree-loading"><i class="fa fa-spinner fa-2x fa-spin"></i></div>',
			'open-icon' : 'fa-minus-square',
			'close-icon' : 'fa-plus-square',
			'selectable' : true,
			'selected-icon' : 'fa-check',
			'unselected-icon' : 'fa-times'
		});
		$('#tree2').admin_tree({
			dataSource: treeDataSource2 ,
			loadingHTML:'<div class="tree-loading"><i class="fa fa-spinner fa-2x fa-spin"></i></div>',
			'open-icon' : 'fa-folder-open',
			'close-icon' : 'fa-folder',
			'selectable' : false,
			'selected-icon' : null,
			'unselected-icon' : null
		});
		
		
		//To add font awesome support
		$('.tree').find('[class*="fa-"]').addClass("fa");
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	Nestable Lists
	/*-----------------------------------------------------------------------------------*/
	var handleNestableLists = function () {
		var updateOutput = function(e)
		{
			var list   = e.length ? e : $(e.target),
				output = list.data('output');
			if (window.JSON) {
				output.val(window.JSON.stringify(list.nestable('serialize')));//, null, 2));
			} else {
				output.val('JSON browser support required for this demo.');
			}
		};

		// activate Nestable for list 1
		$('#nestable').nestable({
			group: 1
		})
		.on('change', updateOutput);
		
		// activate Nestable for list 2
		$('#nestable2').nestable({
			group: 1
		})
		.on('change', updateOutput);

		// output initial serialised data
		updateOutput($('#nestable').data('output', $('#nestable-output')));
		updateOutput($('#nestable2').data('output', $('#nestable2-output')));

		$('#nestable-menu').on('click', function(e)
		{
			var target = $(e.target),
				action = target.data('action');
			if (action === 'expand-all') {
				$('.dd').nestable('expandAll');
			}
			if (action === 'collapse-all') {
				$('.dd').nestable('collapseAll');
			}
		});

		$('#nestable3').nestable();
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Table Cloth
	/*-----------------------------------------------------------------------------------*/
	var handleTablecloth = function () {
		$("#example-dark").tablecloth({ 
			theme: "dark"
		});
		$("#example-paper").tablecloth({
		  theme:"paper",
		  striped: true
		});
		$("#example-stats").tablecloth({
		  theme:"stats",
		  sortable:true,
		  condensed:true,
		  striped:true,
		  clean:true
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Data Tables
	/*-----------------------------------------------------------------------------------*/
	var handleDataTables = function () {
		$('#datatable1').dataTable({
				"sPaginationType": "bs_full"
			});
		$('#datatable2').dataTable({
				"sPaginationType": "bs_full",
				sDom: "<'row'<'dataTables_header clearfix'<'col-md-4'l><'col-md-8'Tf>r>>t<'row'<'dataTables_footer clearfix'<'col-md-6'i><'col-md-6'p>>>",
                oTableTools: {
                    aButtons: ["copy", "print", "csv", "xls", "pdf"],
                    sSwfPath: "js/datatables/extras/TableTools/media/swf/copy_csv_xls_pdf.swf"
                }
			});
		$('.datatable').each(function(){
			var datatable = $(this);
			// SEARCH - Add the placeholder for Search and Turn this into in-line form control
			var search_input = datatable.closest('.dataTables_wrapper').find('div[id$=_filter] input');
			search_input.attr('placeholder', 'Search');
			search_input.addClass('form-control input-sm');
			// LENGTH - Inline-Form control
			var length_sel = datatable.closest('.dataTables_wrapper').find('div[id$=_length] select');
			length_sel.addClass('form-control input-sm');
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	jqGrid
	/*-----------------------------------------------------------------------------------*/
	var handleJqgrid = function () {
		var grid_data = 
			[ 
				{id:"1",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"2",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"3",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"4",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"5",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"6",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"7",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"8",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"9",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"10",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"11",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"12",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"13",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"14",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"15",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"16",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"17",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"18",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"19",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"20",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"21",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"22",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"23",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"24",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"},
				{id:"25",invdate:"2007-12-03",name:"Client1",amount:"1000.00",tax:"140.00",total:"1000.00", note:"This is a note"}
			];
		jQuery("#rowed3").jqGrid({
			data: grid_data,
			datatype: "local",
			height: 250,
			colNames: ['Inv No', 'Date', 'Client', 'Amount', 'Tax', 'Total', 'Notes'],
			colModel: [{
				name: 'id',
				index: 'id',
				width: 55
			}, {
				name: 'invdate',
				index: 'invdate',
				width: 90,
				editable: true
			}, {
				name: 'name',
				index: 'name',
				width: 100,
				editable: true
			}, {
				name: 'amount',
				index: 'amount',
				width: 80,
				align: "right",
				editable: true
			}, {
				name: 'tax',
				index: 'tax',
				width: 80,
				align: "right",
				editable: true
			}, {
				name: 'total',
				index: 'total',
				width: 80,
				align: "right",
				editable: true
			}, {
				name: 'note',
				index: 'note',
				width: 150,
				sortable: false,
				editable: true
			}],
			rowNum: 10,
			rowList: [10, 20, 30],
			pager: '#prowed3',
			sortname: 'id',
			viewrecords: true,
			sortorder: "asc",
			editurl: "server.html",
			caption: "Inline navigator",
			autowidth: true
			});
			jQuery("#rowed3").jqGrid('navGrid', "#prowed3", {
				edit: false,
				add: false,
				del: false
			});
			jQuery("#rowed3").jqGrid('inlineNav', "#prowed3");
			/* Add tooltips */
			$('.navtable .ui-pg-button').tooltip({container:'body'});
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	Typeahead
	/*-----------------------------------------------------------------------------------*/
	var handleTypeahead = function () {
		$('#autocomplete-example').typeahead({
			name: 'countries',
			local: ["red", "blue", "green", "yellow", "brown", "black"]
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Autosize
	/*-----------------------------------------------------------------------------------*/
	var handleAutosize = function () {
		$('textarea.autosize').autosize();
		$('textarea.autosize').addClass('textarea-transition');
	}
	/*-----------------------------------------------------------------------------------*/
	/*	jquery Counatble
	/*-----------------------------------------------------------------------------------*/
	var handleCountable = function () {
		$('.countable').simplyCountable();
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Select2
	/*-----------------------------------------------------------------------------------*/
	var handleSelect2 = function () {
		function movieFormatResult(movie) {
			var markup = "<table class='movie-result'><tr>";
			if (movie.posters !== undefined && movie.posters.thumbnail !== undefined) {
				markup += "<td class='movie-image'><img src='" + movie.posters.thumbnail + "'/></td>";
			}
			markup += "<td class='movie-info'><div class='movie-title'>" + movie.title + "</div>";
			if (movie.critics_consensus !== undefined) {
				markup += "<div class='movie-synopsis'>" + movie.critics_consensus + "</div>";
			}
			else if (movie.synopsis !== undefined) {
				markup += "<div class='movie-synopsis'>" + movie.synopsis + "</div>";
			}
			markup += "</td></tr></table>"
			return markup;
		}

		function movieFormatSelection(movie) {
			return movie.title;
		}
		/* Basic */
		$("#e1").select2();
		/* Multi-Value Select Boxes */
		$("#e2").select2();
		/* With Placeholders */
		$("#e3").select2({
			 placeholder: "Select a State",
			 allowClear: true
		});
		/* With Placeholders */
		$("#e4").select2({
			 placeholder: "Select a State"
		});
		/* Minimum Input */
		$("#e5").select2({
			  placeholder: "Select 2 characters",
			  minimumInputLength: 2
		});
		/* Maximum Selection Size */
		$("#e6").select2({
			  placeholder: "Select a maximum of 3 states",
			  maximumSelectionSize: 3
		});
		/* Loading Remote Data */
		    $("#e7").select2({
				placeholder: "Search for a movie",
				minimumInputLength: 1,
				ajax: { // instead of writing the function to execute the request we use Select2's convenient helper
					url: "http://api.rottentomatoes.com/api/public/v1.0/movies.json",
					dataType: 'jsonp',
					data: function (term, page) {
					return {
						q: term, // search term
						page_limit: 10,
						apikey: "uekzdmffsrmqzwdtcgmc5yu9" //please do not copy API. Use your own. Copying will be treated as a violation - Cloud Admin Author
						};
					},
					results: function (data, page) { // parse the results into the format expected by Select2.
						// since we are using custom formatting functions we do not need to alter remote JSON data
						return {results: data.movies};
					}
					},
					initSelection: function(element, callback) {
					// the input tag has a value attribute preloaded that points to a preselected movie's id
					// this function resolves that id attribute to an object that select2 can render
					// using its formatResult renderer - that way the movie name is shown preselected
					var id=$(element).val();
					if (id!=="") {
						$.ajax("http://api.rottentomatoes.com/api/public/v1.0/movies/"+id+".json", {
						data: {
						apikey: "uekzdmffsrmqzwdtcgmc5yu9" //please do not copy API. Use your own. Copying will be treated as a violation - Cloud Admin Author
						},
						dataType: "jsonp"
						}).done(function(data) { callback(data); });
					}
					},
					formatResult: movieFormatResult, // omitted for brevity, see the source of this page
					formatSelection: movieFormatSelection, // omitted for brevity, see the source of this page
					dropdownCssClass: "bigdrop", // apply css that makes the dropdown taller
					escapeMarkup: function (m) { return m; } // we do not want to escape markup since we are displaying html in results
			});
		/* Tagging Support */
		    $("#e8").select2({
				tags:["red", "green", "blue"]
			});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Uniform
	/*-----------------------------------------------------------------------------------*/
	var handleUniform = function () {
		$(".uniform").uniform();
	}
	/*-----------------------------------------------------------------------------------*/
	/*	All Checkboxes
	/*-----------------------------------------------------------------------------------*/
	var handleAllUniform = function () {
		$("select, input[type='checkbox']").uniform();
	}
	/*-----------------------------------------------------------------------------------*/
	/*	BT Wysiwyg
	/*-----------------------------------------------------------------------------------*/
	var handleWysiwyg = function () {
		/* Init Bootstrap WYSIWYG */
		function initToolbarBootstrapBindings() {
		  var fonts = ['Serif', 'Sans', 'Arial', 'Arial Black', 'Courier', 
				'Courier New', 'Comic Sans MS', 'Helvetica', 'Impact', 'Lucida Grande', 'Lucida Sans', 'Tahoma', 'Times',
				'Times New Roman', 'Verdana'],
				fontTarget = $('[title=Font]').siblings('.dropdown-menu');
		  $.each(fonts, function (idx, fontName) {
			  fontTarget.append($('<li><a data-edit="fontName ' + fontName +'" style="font-family:\''+ fontName +'\'">'+fontName + '</a></li>'));
		  });
		  $('a[title]').tooltip({container:'body'});
			$('.dropdown-menu input').click(function() {return false;})
				.change(function () {$(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');})
			.keydown('esc', function () {this.value='';$(this).change();});

		  $('[data-role=magic-overlay]').each(function () { 
			var overlay = $(this), target = $(overlay.data('target')); 
			overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
		  });
		  if ("onwebkitspeechchange"  in document.createElement("input")) {
			var editorOffset = $('#editor').offset();
			$('#voiceBtn').css('position','absolute').offset({top: editorOffset.top, left: editorOffset.left+$('#editor').innerWidth()-35});
		  } else {
			$('#voiceBtn').hide();
		  }
		};
		function showErrorAlert (reason, detail) {
			var msg='';
			if (reason==='unsupported-file-type') { msg = "Unsupported format " +detail; }
			else {
				console.log("error uploading file", reason, detail);
			}
			$('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>'+ 
			 '<strong>File upload error</strong> '+msg+' </div>').prependTo('#alerts');
		};
		initToolbarBootstrapBindings();  
		$('#editor').wysiwyg({ fileUploadError: showErrorAlert} );
		/* Disable auto-inline */
		CKEDITOR.disableAutoInline = true;
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Dropzone
	/*-----------------------------------------------------------------------------------*/
	var handleDropzone = function () {
		try {
			  $(".dropzone").dropzone({
			    paramName: "file", // The name that will be used to transfer the file
			    maxFilesize: 0.5, // MB
			  
				addRemoveLinks : true,
				dictResponseError: 'Error while uploading file!',
				
				//change the previewTemplate to use Bootstrap progress bars
				previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n    <div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" data-dz-size></div>\n    <img data-dz-thumbnail />\n  </div>\n  <div class=\"progress progress-sm progress-striped active\"><div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  <div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\"><span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>"
			  });
			} catch(e) {
			  alert('Dropzone.js does not support older browsers!');
			}
	}
	/*-----------------------------------------------------------------------------------*/
	/*	XCharts
	/*-----------------------------------------------------------------------------------*/
	var handleXcharts = function () {
		
		//Dynamic Chart
		function chart1() {
			var data = [{"xScale":"ordinal","comp":[],"main":[{"className":".main.l1","data":[{"y":15,"x":"2012-11-19T00:00:00"},{"y":11,"x":"2012-11-20T00:00:00"},{"y":8,"x":"2012-11-21T00:00:00"},{"y":10,"x":"2012-11-22T00:00:00"},{"y":1,"x":"2012-11-23T00:00:00"},{"y":6,"x":"2012-11-24T00:00:00"},{"y":8,"x":"2012-11-25T00:00:00"}]},{"className":".main.l2","data":[{"y":29,"x":"2012-11-19T00:00:00"},{"y":33,"x":"2012-11-20T00:00:00"},{"y":13,"x":"2012-11-21T00:00:00"},{"y":16,"x":"2012-11-22T00:00:00"},{"y":7,"x":"2012-11-23T00:00:00"},{"y":18,"x":"2012-11-24T00:00:00"},{"y":8,"x":"2012-11-25T00:00:00"}]}],"type":"line-dotted","yScale":"linear"},{"xScale":"ordinal","comp":[],"main":[{"className":".main.l1","data":[{"y":12,"x":"2012-11-19T00:00:00"},{"y":18,"x":"2012-11-20T00:00:00"},{"y":8,"x":"2012-11-21T00:00:00"},{"y":7,"x":"2012-11-22T00:00:00"},{"y":6,"x":"2012-11-23T00:00:00"},{"y":12,"x":"2012-11-24T00:00:00"},{"y":8,"x":"2012-11-25T00:00:00"}]},{"className":".main.l2","data":[{"y":29,"x":"2012-11-19T00:00:00"},{"y":33,"x":"2012-11-20T00:00:00"},{"y":13,"x":"2012-11-21T00:00:00"},{"y":16,"x":"2012-11-22T00:00:00"},{"y":7,"x":"2012-11-23T00:00:00"},{"y":18,"x":"2012-11-24T00:00:00"},{"y":8,"x":"2012-11-25T00:00:00"}]}],"type":"cumulative","yScale":"linear"},{"xScale":"ordinal","comp":[],"main":[{"className":".main.l1","data":[{"y":12,"x":"2012-11-19T00:00:00"},{"y":18,"x":"2012-11-20T00:00:00"},{"y":8,"x":"2012-11-21T00:00:00"},{"y":7,"x":"2012-11-22T00:00:00"},{"y":6,"x":"2012-11-23T00:00:00"},{"y":12,"x":"2012-11-24T00:00:00"},{"y":8,"x":"2012-11-25T00:00:00"}]},{"className":".main.l2","data":[{"y":29,"x":"2012-11-19T00:00:00"},{"y":33,"x":"2012-11-20T00:00:00"},{"y":13,"x":"2012-11-21T00:00:00"},{"y":16,"x":"2012-11-22T00:00:00"},{"y":7,"x":"2012-11-23T00:00:00"},{"y":18,"x":"2012-11-24T00:00:00"},{"y":8,"x":"2012-11-25T00:00:00"}]}],"type":"bar","yScale":"linear"}];
			var order = [0, 1, 0, 2],
			  i = 0,
			  xFormat = d3.time.format('%A'),
			  chart = new xChart('line-dotted', data[order[i]], '#chart1', {
				axisPaddingTop: 5,
				dataFormatX: function (x) {
				  return new Date(x);
				},
				tickFormatX: function (x) {
				  return xFormat(x);
				},
				timing: 1250
			  }),
			  rotateTimer,
			  toggles = d3.selectAll('.multi button'),
			  t = 3500;

			function updateChart(i) {
			  var d = data[i];
			  chart.setData(d);
			  toggles.classed('toggled', function () {
				return (d3.select(this).attr('data-type') === d.type);
			  });
			  return d;
			}

			toggles.on('click', function (d, i) {
			  clearTimeout(rotateTimer);
			  updateChart(i);
			});

			function rotateChart() {
			  i += 1;
			  i = (i >= order.length) ? 0 : i;
			  var d = updateChart(order[i]);
			  rotateTimer = setTimeout(rotateChart, t);
			}
			rotateTimer = setTimeout(rotateChart, t);
		}
		
		//Time-Series Line
		function chart2() {
			var data = {
			  "xScale": "time",
			  "yScale": "linear",
			  "type": "line",
			  "main": [
				{
				  "className": ".pizza",
				  "data": [
					{
					  "x": "2012-11-05",
					  "y": 1
					},
					{
					  "x": "2012-11-06",
					  "y": 6
					},
					{
					  "x": "2012-11-07",
					  "y": 13
					},
					{
					  "x": "2012-11-08",
					  "y": -3
					},
					{
					  "x": "2012-11-09",
					  "y": -4
					},
					{
					  "x": "2012-11-10",
					  "y": 9
					},
					{
					  "x": "2012-11-11",
					  "y": 6
					}
				  ]
				}
			  ]
			};
			var opts = {
			  "dataFormatX": function (x) { return d3.time.format('%Y-%m-%d').parse(x); },
			  "tickFormatX": function (x) { return d3.time.format('%A')(x); }
			};
			var myChart = new xChart('line', data, '#chart2', opts);
		}
		
		function chart3() {
			var tt = document.createElement('div'),
			  leftOffset = -(~~$('html').css('padding-left').replace('px', '') + ~~$('body').css('margin-left').replace('px', '')),
			  topOffset = -32;
			tt.className = 'ex-tooltip';
			document.body.appendChild(tt);

			var data = {
			  "xScale": "time",
			  "yScale": "linear",
			  "main": [
				{
				  "className": ".pizza",
				  "data": [
					{
					  "x": "2012-11-05",
					  "y": 6
					},
					{
					  "x": "2012-11-06",
					  "y": 6
					},
					{
					  "x": "2012-11-07",
					  "y": 8
					},
					{
					  "x": "2012-11-08",
					  "y": 3
					},
					{
					  "x": "2012-11-09",
					  "y": 4
					},
					{
					  "x": "2012-11-10",
					  "y": 9
					},
					{
					  "x": "2012-11-11",
					  "y": 6
					}
				  ]
				}
			  ]
			};
			var opts = {
			  "dataFormatX": function (x) { return d3.time.format('%Y-%m-%d').parse(x); },
			  "tickFormatX": function (x) { return d3.time.format('%A')(x); },
			  "mouseover": function (d, i) {
				var pos = $(this).offset();
				$(tt).text(d3.time.format('%A')(d.x) + ': ' + d.y)
				  .css({top: topOffset + pos.top, left: pos.left + leftOffset})
				  .show();
			  },
			  "mouseout": function (x) {
				$(tt).hide();
			  }
			};
			var myChart = new xChart('line-dotted', data, '#chart3', opts);
		}
		
		function chart4() {
			var data = {
			  "xScale": "ordinal",
			  "yScale": "linear",
			  "main": [
				{
				  "className": ".pizza",
				  "data": [
					{
					  "x": "Pepperoni",
					  "y": 4
					},
					{
					  "x": "Cheese",
					  "y": 8
					}
				  ]
				}
			  ]
			};
			var myChart = new xChart('bar', data, '#chart4');
		}
		
		function chart5() {
			var data = {
			  "xScale": "ordinal",
			  "yScale": "linear",
			  "main": [
				{
				  "className": ".pizza",
				  "data": [
					{
					  "x": "Pepperoni",
					  "y": 4
					},
					{
					  "x": "Cheese",
					  "y": 8
					}
				  ]
				},
				{
				  "className": ".pizza",
				  "data": [
					{
					  "x": "Pepperoni",
					  "y": 6
					},
					{
					  "x": "Cheese",
					  "y": 5
					}
				  ]
				}
			  ]
			};
			var myChart = new xChart('bar', data, '#chart5');
		}
		
		function chart6() {
			var errorBar = {
			  enter: function (self, storage, className, data, callbacks) {
				var insertionPoint = xChart.visutils.getInsertionPoint(9),
				  container,
				  // Map each error bar into 3 points, so it's easier to draw as a single path
				  // Converts each point to a triplet with y from (y - e) to (y + e)
				  // It would be better to use the `preUpdateScale` method here,
				  // but for this quick example, we're taking a shortcut :)
				  eData = data.map(function (d) {
					d.data = d.data.map(function (d) {
					  return [{x: d.x, y: d.y - d.e}, {x: d.x, y: d.y}, {x: d.x, y: d.y + d.e}];
					});
					return d;
				  }),
				  paths;

				// It's always a good idea to create containers for sets
				container = self._g.selectAll('.errorLine' + className)
				  .data(eData, function (d) {
					return d.className;
				  });

				// The insertionPoint is a special method that helps us insert this
				// vis at a particular z-index
				// In this case, we've chosen the highest point (above everything else)
				container.enter().insert('g', insertionPoint)
				  .attr('class', function (d, i) {
					return 'errorLine' + className.replace(/\./g, ' ') +
					  ' color' + i;
				  });

				// Tell each path about its data
				// and ensure we reuse any previously drawn item
				paths = container.selectAll('path')
				  .data(function (d) {
					return d.data;
				  }, function (d) {
					return d[0].x;
				  });

				paths.enter().insert('path')
				  .style('opacity', 0)
				  .attr('d', d3.svg.line()
					.x(function (d) {
					  // We offset by half the rangeBand, because this is a bar chart
					  return self.xScale(d.x) + self.xScale.rangeBand() / 2;
					})
					.y(function (d) { return self.yScale(d.y); })
				  );

				storage.containers = container;
				storage.paths = paths;
			  },
			  update: function (self, storage, timing) {
				// This is mostly duplication to the d3.svg.line from the enter() method
				storage.paths.transition().duration(timing)
				  .style('opacity', 1)
				  .attr('d', d3.svg.line()
					.x(function (d) {
					  return self.xScale(d.x) + self.xScale.rangeBand() / 2;
					})
					.y(function (d) { return self.yScale(d.y); })
				  );
			  },
			  exit: function (self, storage, timing) {
				storage.paths.exit()
				  .transition().duration(timing)
				  .style('opacity', 0);
			  },
			  destroy: function (self, storage, timing) {
				storage.paths.transition().duration(timing)
				  .style('opacity', 0)
				  .remove();
			  }
			};
			
			xChart.setVis('error', errorBar);
			
			var data = [{
				  "xScale": "ordinal",
				  "yScale": "linear",
				  "main": [
					{
					  "className": ".errorExample",
					  "data": [
						{
						  "x": "Ponies",
						  "y": 12
						},
						{
						  "x": "Unicorns",
						  "y": 23
						},
						{
						  "x": "Trolls",
						  "y": 1
						}
					  ]
					}
				  ],
				  "comp": [
					{
					  "type": "error",
					  "className": ".comp.errorBar",
					  "data": [
						{
						  "x": "Ponies",
						  "y": 12,
						  "e": 5
						},
						{
						  "x": "Unicorns",
						  "y": 23,
						  "e": 2
						},
						{
						  "x": "Trolls",
						  "y": 1,
						  "e": 1
						}
					  ]
					}
				  ]
				},
				{
				  "xScale": "ordinal",
				  "yScale": "linear",
				  "main": [
					{
					  "className": ".errorExample",
					  "data": [
						{
						  "x": "Ponies",
						  "y": 76
						},
						{
						  "x": "Unicorns",
						  "y": 45
						},
						{
						  "x": "Trolls",
						  "y": 82
						}
					  ]
					}
				  ],
				  "comp": [
					{
					  "type": "error",
					  "className": ".comp.errorBar",
					  "data": [
						{
						  "x": "Ponies",
						  "y": 76,
						  "e": 12
						},
						{
						  "x": "Unicorns",
						  "y": 45,
						  "e": 3
						},
						{
						  "x": "Trolls",
						  "y": 82,
						  "e": 12
						}
					  ]
					}
				  ]
				}
			  ];
			  
			 var myChart = new xChart('bar', data[0], '#chart6'), i = 0;

			  function timer() {
				setTimeout(function () {
				  timer();
				  i += 1;
				  myChart.setData(data[i % 2]);
				}, 3000);
			  }
			  timer();
		}
		
		//Run all charts
		chart1();
		chart2();
		chart3();
		chart4();
		chart5();
		chart6();
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Justgage
	/*-----------------------------------------------------------------------------------*/
	var handleGage = function () {
		var g1, g2, g3, g4, g5, g6;
      
      window.onload = function(){
      var g1 = new JustGage({
        id: "g1", 
        value: getRandomInt(0, 100), 
        min: 0,
        max: 100,
        title: "Custom Width",
        label: "",    
        gaugeWidthScale: 0.2
      });
      
      var g2 = new JustGage({
        id: "g2", 
        value: getRandomInt(0, 100), 
        min: 0,
        max: 100,
        title: "Custom Shadow",
        label: "",    
        shadowOpacity: 1,
        shadowSize: 0,
        shadowVerticalOffset: 4	
      });
      
      var g3 = new JustGage({
        id: "g3", 
        value: getRandomInt(0, 100), 
        min: 0,
        max: 100,
        title: "Custom Colors",
        label: "",  
        levelColors: [Theme.colors.red, Theme.colors.yellow, Theme.colors.green]
      });
      
      var g4 = new JustGage({
        id: "g4", 
        value: getRandomInt(0, 100), 
        min: 0,
        max: 100,
        title: "Hide Labels",
        showMinMax: false		
      });
     
      
      var g5 = new JustGage({
        id: "g5", 
        value: getRandomInt(0, 100), 
        min: 0,
        max: 100,
        title: "Animation Type",
        label: "",  
        startAnimationTime: 2000,
        startAnimationType: ">",
        refreshAnimationTime: 1000,
        refreshAnimationType: "bounce"			
      });
      
      var g6 = new JustGage({
        id: "g6", 
        value: getRandomInt(0, 100), 
        min: 0,
        max: 100,
        title: "Minimal",
        label: "",  
        showMinMax: false,
        gaugeColor: "#E6E6E6",
        levelColors: ["#555555"],
        showInnerShadow: false,        
        startAnimationTime: 1,
        startAnimationType: "linear",
        refreshAnimationTime: 1,
        refreshAnimationType: "linear"          
      });
      
        setInterval(function() {
          g1.refresh(getRandomInt(0, 100));
          g2.refresh(getRandomInt(0, 100));          
          g3.refresh(getRandomInt(0, 100));
          g4.refresh(getRandomInt(0, 100));
          g5.refresh(getRandomInt(0, 100));          
          g6.refresh(getRandomInt(0, 100));
        }, 2500);
      };
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Easy Pie chart
	/*-----------------------------------------------------------------------------------*/
	var handleEasyPie = function () {
		//Pie 1
		$('#pie_1').easyPieChart({
			easing: 'easeOutBounce',
			onStep: function(from, to, percent) {
				$(this.el).find('.percent').text(Math.round(percent));
			},
			lineWidth: 3,
			barColor: '#A8BC7B'
		});
		var chart1 = window.chart = $('#pie_1').data('easyPieChart');
		$('#js_update_1').on('click', function() {
			chart1.update(Math.random()*100);
		});
		
		//Pie 2
		$('#pie_2').easyPieChart({
			easing: 'easeOutBounce',
			onStep: function(from, to, percent) {
				$(this.el).find('.percent').text(Math.round(percent));
			},
			lineWidth: 6,
			barColor: '#F0AD4E'
		});
		var chart2 = window.chart = $('#pie_2').data('easyPieChart');
		$('#js_update_2').on('click', function() {
			chart2.update(Math.random()*100);
		});
		
		//Pie 3
		$('#pie_3').easyPieChart({
			easing: 'easeOutBounce',
			onStep: function(from, to, percent) {
				$(this.el).find('.percent').text(Math.round(percent));
			},
			lineWidth: 9,
			barColor: '#D9534F'
		});
		var chart3 = window.chart = $('#pie_3').data('easyPieChart');
		$('#js_update_3').on('click', function() {
			chart3.update(Math.random()*100);
		});
		
		//Pie 4
		$('#pie_4').easyPieChart({
			easing: 'easeOutBounce',
			onStep: function(from, to, percent) {
				$(this.el).find('.percent').text(Math.round(percent));
			},
			lineWidth: 12,
			barColor: '#70AFC4',
			lineCap: 'butt'
		});
		var chart4 = window.chart = $('#pie_4').data('easyPieChart');
		$('#js_update_4').on('click', function() {
			chart4.update(Math.random()*100);
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Easy Pie chart for profile
	/*-----------------------------------------------------------------------------------*/
	var handleProfileSkillPie = function () {
		
		//Pie 1
		$('#pie_1').easyPieChart({
			easing: 'easeOutBounce',
			onStep: function(from, to, percent) {
				$(this.el).find('.percent').text(Math.round(percent)+"%");
			},
			lineWidth: 6,
			barColor: '#F0AD4E'
		});
		var chart1 = window.chart = $('#pie_1').data('easyPieChart');
		
		//Pie 2
		$('#pie_2').easyPieChart({
			easing: 'easeOutBounce',
			onStep: function(from, to, percent) {
				$(this.el).find('.percent').text(Math.round(percent)+"%");
			},
			lineWidth: 6,
			barColor: '#D9534F'
		});
		var chart2 = window.chart = $('#pie_2').data('easyPieChart');
		
		//Pie 3
		$('#pie_3').easyPieChart({
			easing: 'easeOutBounce',
			onStep: function(from, to, percent) {
				$(this.el).find('.percent').text(Math.round(percent)+"%");
			},
			lineWidth: 6,
			barColor: '#70AFC4'
		});
		var chart3 = window.chart = $('#pie_3').data('easyPieChart');
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Sparklines
	/*-----------------------------------------------------------------------------------*/
	var handleSparkline = function () {
		//Sparkline bar
		$(".sparkline").each(function() {
		  var barSpacing, barWidth, color, height;
		  color = $(this).attr("data-color") || "red";
		  height = "18px";
		  if ($(this).hasClass("big")) {
			barWidth = "5px";
			barSpacing = "2px";
			height = "40px";
		  }
		  return $(this).sparkline("html", {
			type: "bar",
			barColor: Theme.colors[color],
			height: height,
			barWidth: barWidth,
			barSpacing: barSpacing,
			zeroAxis: false
		  });
		});
		//Sparkline Pie
		$(".sparklinepie").each(function() {
		  var height;
		  height = "50px";
		  if ($(this).hasClass("big")) {
			height = "70px";
		  }
		  return $(this).sparkline("html", {
			type: "pie",
			height: height,
			sliceColors: [Theme.colors.blue, Theme.colors.red, Theme.colors.green, Theme.colors.orange]
		  });
		});
		//Sparkline Line
		$(".linechart").each(function() {
		  var height;
		  height = "18px";
		  if ($(this).hasClass("linechart-lg")) {
			height = "30px";
		  }
		  return $(this).sparkline("html", {
			type: "line",
			height: height,
			width: "150px",
			minSpotColor: Theme.colors.red,
			maxSpotColor: Theme.colors.green,
			spotRadius: 3,
			lineColor: Theme.colors.primary,
			fillColor: "rgba(94,135,176,0.1)",
			lineWidth: 1.2,
			highlightLineColor: Theme.colors.red,
			highlightSpotColor: Theme.colors.yellow
		  });
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Fullcalendar
	/*-----------------------------------------------------------------------------------*/
	var handleCalendar = function () {
		/* initialize the external events
		-----------------------------------------------------------------*/
	
		var initDrag = function (el) {
		
			// create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
			// it doesn't need to have a start or end
			var eventObject = {
				title: $.trim(el.text()) // use the element's text as the event title
			};
			
			// store the Event Object in the DOM element so we can get to it later
			el.data('eventObject', eventObject);
			
			// make the event draggable using jQuery UI
			el.draggable({
				zIndex: 999,
				revert: true,      // will cause the event to go back to its
				revertDuration: 0  //  original position after the drag
			});
			
		}
		
		var addEvent = function (title) {
            title = title.length == 0 ? "Untitled Event" : title;
            var html = $('<div class="external-event">' + title + '</div>');
            jQuery('#event-box').append(html);
            initDrag(html);
        }

        $('#external-events div.external-event').each(function () {
            initDrag($(this))
        });

        $('#add-event').unbind('click').click(function () {
            var title = $('#event-title').val();
            addEvent(title);
        });
	
	
		/* initialize the calendar
		-----------------------------------------------------------------*/
		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		
		var calendar = $('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			selectable: true,
			selectHelper: true,
			select: function(start, end, allDay) {
				var title = prompt('Event Title:');
				if (title) {
					calendar.fullCalendar('renderEvent',
						{
							title: title,
							start: start,
							end: end,
							allDay: allDay
						},
						true // make the event "stick"
					);
				}
				calendar.fullCalendar('unselect');
			},
			editable: true,
			editable: true,
			droppable: true, // this allows things to be dropped onto the calendar !!!
			drop: function(date, allDay) { // this function is called when something is dropped
			
				// retrieve the dropped element's stored Event Object
				var originalEventObject = $(this).data('eventObject');
				
				// we need to copy it, so that multiple events don't have a reference to the same object
				var copiedEventObject = $.extend({}, originalEventObject);
				
				// assign it the date that was reported
				copiedEventObject.start = date;
				copiedEventObject.allDay = allDay;
				
				// render the event on the calendar
				// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
				$('#calendar').fullCalendar('renderEvent', copiedEventObject, true);
				
				// is the "remove after drop" checkbox checked?
				if ($('#drop-remove').is(':checked')) {
					// if so, remove the element from the "Draggable Events" list
					$(this).remove();
				}
				
			},
			events: [
				{
					title: 'All Day Event',
					start: new Date(y, m, 1),
					backgroundColor: Theme.colors.blue,
				},
				{
					title: 'Long Event',
					start: new Date(y, m, d-5),
					end: new Date(y, m, d-2),
					backgroundColor: Theme.colors.red,
				},
				{
					id: 999,
					title: 'Repeating Event',
					start: new Date(y, m, d-3, 16, 0),
					allDay: false,
					backgroundColor: Theme.colors.yellow,
				},
				{
					id: 999,
					title: 'Repeating Event',
					start: new Date(y, m, d+4, 16, 0),
					allDay: false,
					backgroundColor: Theme.colors.primary,
				},
				{
					title: 'Meeting',
					start: new Date(y, m, d, 10, 30),
					allDay: false,
					backgroundColor: Theme.colors.green,
				},
				{
					title: 'Lunch',
					start: new Date(y, m, d, 12, 0),
					end: new Date(y, m, d, 14, 0),
					allDay: false,
					backgroundColor: Theme.colors.red,
				},
				{
					title: 'Birthday Party',
					start: new Date(y, m, d+1, 19, 0),
					end: new Date(y, m, d+1, 22, 30),
					allDay: false,
					backgroundColor: Theme.colors.gray,
				},
				{
					title: 'Click for Google',
					start: new Date(y, m, 28),
					end: new Date(y, m, 29),
					url: 'http://google.com/',
					backgroundColor: Theme.colors.green,
				}
			]
		});
		
	}
	/*-----------------------------------------------------------------------------------*/
	/*	JQVmaps
	/*-----------------------------------------------------------------------------------*/
	var handleJqvmaps = function () {
		var setMap = function (name) {
			var data = {
				map: 'world_en',
				backgroundColor: null,
				borderColor: '#333333',
				borderOpacity: 0.5,
				borderWidth: 1,
				color:	Theme.colors.blue,
				enableZoom: true,
				hoverColor: Theme.colors.yellow,
				hoverOpacity: null,
				values: sample_data,
				normalizeFunction: 'linear',
				scaleColors: ['#b6da93', '#427d1a'],
				selectedColor: '#c9dfaf',
				selectedRegion: null,
				showTooltip: true,
				onRegionOver: function (event, code) {
					//sample to interact with map
					if (code == 'ca') {
						event.preventDefault();
					}
				},
				onRegionClick: function (element, code, region) {
					//sample to interact with map
					var message = 'You clicked "' + region + '" which has the code: ' + code.toUpperCase();
					alert(message);
				}
			};

			data.map = name + '_en';
			var map = jQuery('#vmap_' + name);
			if (!map) {
				return;
			}
			map.width(map.parent().width());
			map.vectorMap(data);
		}
		
		//Init the maps
		setMap("world");
        setMap("usa");
        setMap("europe");
        setMap("russia");
        setMap("germany");
		App.addResponsiveFunction(function(){
            setMap("world");
            setMap("usa");
            setMap("europe");
            setMap("russia");
            setMap("germany");
        });
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	Isotope
	/*-----------------------------------------------------------------------------------*/
	var handleIsotope = function () {
		// cache container
		var $container = $('#filter-items');
		// initialize isotope after image loaded
		$container.imagesLoaded( function(){
			$container.isotope({
			  // options...
			});

			// filter items when filter link is clicked
			$('#filter-controls a').click(function(){
			  var selector = $(this).attr('data-filter');
			  $container.isotope({ filter: selector });
			  return false;
			});
			// filter on smaller screens
			$("#e1").change(function(){
				var selector = $(this).find(":selected").val();
				 $container.isotope({ filter: selector });
				 return false;
			});
		});
		
		function handleIsotopeStretch() {
			var width = $(window).width();
			if ( width < 768 ) {
				$('#filter-items .item').addClass('width-100');
			}
			else {
				$('#filter-items .item').removeClass('width-100');
			}
		}
		handleIsotopeStretch();
		/* On Resize show menu on desktop if hidden */
		jQuery(window).resize(function() {
			handleIsotopeStretch();
		});
	}
	
	/*-----------------------------------------------------------------------------------*/
	/*	Handle hover in gallery
	/*-----------------------------------------------------------------------------------*/
	var handleHover = function () {
		$('.filter-content').hover(function() {
			var hoverContent = $(this).children('.hover-content');
			hoverContent.removeClass('fadeOut').addClass('animated fadeIn').show();
		}, function() {
			var hoverContent = $(this).children('.hover-content');
			hoverContent.removeClass('fadeIn').addClass('fadeOut');
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handle Colorbox
	/*-----------------------------------------------------------------------------------*/
	var handleColorbox = function () {
		$('.colorbox-button').colorbox({rel:'colorbox-button',maxWidth:'95%', maxHeight:'95%'});
		/* Colorbox resize function */
		var resizeTimer;
		function resizeColorBox()
		{
			if (resizeTimer) clearTimeout(resizeTimer);
			resizeTimer = setTimeout(function() {
					var myWidth = 442, percentageWidth = .95;
					if (jQuery('#cboxOverlay').is(':visible')) {
						$.colorbox.resize({ width: ( $(window).width() > ( myWidth+20) )? myWidth : Math.round( $(window).width()*percentageWidth ) });
						$('.cboxPhoto').css( {
							width: $('#cboxLoadedContent').innerWidth(),
							height: 'auto'
						});
						$('#cboxLoadedContent').height( $('.cboxPhoto').height() );
						$.colorbox.resize();
					}
			}, 300)
		}

		// Resize Colorbox when resizing window or changing mobile device orientation
		jQuery(window).resize(resizeColorBox);
		window.addEventListener("orientationchange", resizeColorBox, false);
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handle Backstretch
	/*-----------------------------------------------------------------------------------*/
	var handleBackstretch = function () {
		 $.backstretch([
		"[[${#request.getContextPath()}]]/components/cloudadmin/dist/img/login/1.jpg"
		, "[[${#request.getContextPath()}]]/components/cloudadmin/dist/img/login/2.jpg"
		, "[[${#request.getContextPath()}]]/components/cloudadmin/dist/img/login/3.jpg"
		, "[[${#request.getContextPath()}]]/components/cloudadmin/dist/img/login/4.jpg"
		], {duration: 3000, fade: 750});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handle Chat
	/*-----------------------------------------------------------------------------------*/
	var handleChat = function (elem) {
		var append = function() {
			//Check if chat is empty
            var input = $('.'+elem+' .chat-form input');
            var text = input.val();
            if (text.length == 0) {
                return;
            }
			
			//Get time
			var curr_time = moment().format('YYYY-MM-DD HH:mm:ss');
			
			var msg = '';
                msg +='<li class="animated fadeInLeft media">';
				msg +=  '<a class="pull-right" href="#">';
				msg +=	'<img class="media-object" alt="Generic placeholder image" src="components/cloudadmin/dist/img/chat/headshot2.jpg">';
				msg +=  '</a>';
				msg +=  '<div class="pull-right media-body chat-pop mod">';
				msg +=	'<h4 class="media-heading">You <span class="pull-left"><abbr id="curr-time" class="timeago" title="'+curr_time+'" >'+curr_time+'</abbr> <i class="fa fa-clock-o"></i></span></h4>';
				msg +=	text;
				msg +=  '</div>';
				msg +='</li>';
				
			var list = $('.'+elem+' .chat-list');
			list.append(msg);
			jQuery("abbr.timeago").timeago();
			input.val("");
            $('.'+elem+' .scroller').slimScroll({
                scrollTo: list.height()
            });
		}
		//If button is pressed
		$('.'+elem+' .chat-form .btn').click(function(e){
			e.preventDefault();
			append();
		});
		
		var input = $('.'+elem+' .chat-form input');
		//If Enter is pressed
		input.keypress(function (e) {
			if (e.which == 13) {
				append();
				return false;
			}
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handle Timeline
	/*-----------------------------------------------------------------------------------*/
	var handleTimeline = function () {
		createStoryJS({
			type:		'timeline',
			width:		'100%',
			height:		'600',
			source:		'js/timelinejs/example_json.json',
			embed_id:	'my-timeline',
			debug:		true,
			css:        'js/timelinejs/css/timeline.css',     
            js:         'js/timelinejs/js/timeline-min.js'    
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handle Slidernav
	/*-----------------------------------------------------------------------------------*/
	var handleSliderNav = function () {
		$('#address-book').sliderNav();
		
		$('#address-book .slider-content ul li ul li a').click(function(e){
			e.preventDefault();
			var contact_card = $('#contact-card');
			//Get the name clicked on
			var name = $(this).text();
			//Set the name
			$('#contact-card .panel-title').html(name);
			$('#contact-card #card-name').html(name);
			//Randomize the image
			var img_id = Math.floor(Math.random() * (5 - 1 + 1)) + 1;
			//Set the image
			$('#contact-card .headshot img').attr('src', 'components/cloudadmin/dist/img/addressbook/'+img_id+'.jpg');
			contact_card.removeClass('animated fadeInUp').addClass('animated fadeInUp');
			var wait = window.setTimeout( function(){
				contact_card.removeClass('animated fadeInUp')},
				1300
			);
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handle Active Toggle
	/*-----------------------------------------------------------------------------------*/
	var handleActiveToggle = function () {
		$('#list-toggle .list-group a').click(function(){
			$('#list-toggle .list-group > a.active').removeClass('active');
			$(this).addClass('active');
		})
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handle Box Sortable
	/*-----------------------------------------------------------------------------------*/
	var handleBoxSortable = function () {
		$('.box-container').sortable({
		    connectWith: '.box-container',
			items:'> .box',
			opacity:0.8,
			revert:true,
			forceHelperSize:true,
			placeholder: 'box-placeholder',
			forcePlaceholderSize:true,
			tolerance:'pointer'
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handles the go to top button at the footer
	/*-----------------------------------------------------------------------------------*/
	var handleGoToTop = function () {
		$('.footer-tools').on('click', '.go-top', function (e) {
			App.scrollTo();
			e.preventDefault();
		});
	} 
	/*-----------------------------------------------------------------------------------*/
	/*	Handles navbar fixed top
	/*-----------------------------------------------------------------------------------*/
	var handleNavbarFixedTop = function () {
		if(is_mobile && is_fixed_header) {
			//Manage margin top
			$('#main-content').addClass('margin-top-100');
		}
		if(!(is_mobile) && is_fixed_header){
			//Manage margin top
			$('#main-content').removeClass('margin-top-100').addClass('margin-top-50');
		}
	} 
	/*-----------------------------------------------------------------------------------*/
	/*	Handles flot charts in dashboard
	/*-----------------------------------------------------------------------------------*/
	var handleDashFlotCharts = function () {
		function chartMonth() { 
			var data1 = [[0, 1.5],[1, 2], [2, 1], [3, 1.5], [4, 2.5],[5, 2], [6, 2], [7, 0.5], [8, 1], [9, 1.5], [10, 2],[11, 2.5], [12, 2], [13, 1.5], [14, 2.8], [15, 2],[16, 3], [17, 2], [18, 2.5], [19, 3],[20, 2.5], [21, 2], [22, 1.5], [23, 2.5], [24, 2], [25, 1.5],[26, 1], [27, 0.5], [28, 1], [29, 1],[30, 1.5], [31, 1]];
			var data2 = [[0, 2.5],[1, 3.5], [2, 2], [3, 3], [4, 4],[5, 3.5], [6, 3.5], [7, 1], [8, 2], [9, 3], [10, 4],[11, 5], [12, 4], [13, 3], [14, 5], [15, 3.5],[16, 5], [17, 4], [18, 5], [19, 6],[20, 5], [21, 4], [22, 3], [23, 5], [24, 4], [25, 3],[26, 2], [27, 1], [28, 2], [29, 2],[30, 3], [31, 2]];
			
			var plot = $.plot($("#chart-dash"), [{
				data: data2,
				label: "Pageviews",
				bars: {
					show: true,
					fill: true,
					barWidth: 0.4,
					align: "center",
					lineWidth: 13
				}
			}, {
				data: data1,
				label: "Visits",
				lines: {
					show: true,
					lineWidth: 2
				},
				points: {
					show: true,
					lineWidth: 2,
					fill: true
				},
				shadowSize: 0
			}, {
				data: data1,
				label: "Visits",
				lines: {
					show: true,
					lineWidth: 1,
					fill: true,
                    fillColor: {
                        colors: [{
                                opacity: 0.05
                            }, {
                                opacity: 0.01
                            }
                        ]
                    }
				},
				points: {
					show: true,
					lineWidth: 0.5,
					fill: true
				},
				shadowSize: 0
			}], {
				grid: {
					hoverable: true,
					clickable: true,
					tickColor: "#f7f7f7",
					borderWidth: 0,
					labelMargin: 10,
					margin: {
						top: 0,
						left: 5,
						bottom: 0,
						right: 0
					}
				},
				legend: {
					show: false
				},
				colors: ["rgba(109,173,189,0.5)", "#70AFC4", "#DB5E8C"],
				
				xaxis: {
					ticks: 5,
					tickDecimals: 0,
					tickColor: "#fff"
				},
				yaxis: {
					ticks: 3,
					tickDecimals: 0
				},
			});
			function showTooltip(x, y, contents) {
                    $('<div id="tooltip">' + contents + '</div>').css({
                            position: 'absolute',
                            display: 'none',
                            top: y + 5,
                            left: x + 15,
                            border: '1px solid #333',
                            padding: '4px',
                            color: '#fff',
                            'border-radius': '3px',
                            'background-color': '#333',
                            opacity: 0.80
                        }).appendTo("body").fadeIn(200);
                }
			var previousPoint = null;
			$("#chart-dash").bind("plothover", function (event, pos, item) {
				$("#x").text(pos.x.toFixed(2));
				$("#y").text(pos.y.toFixed(2));
				if (item) {
					if (previousPoint != item.dataIndex) {
						previousPoint = item.dataIndex;
						$("#tooltip").remove();
						var x = item.datapoint[0].toFixed(2),
							y = item.datapoint[1].toFixed(2);
						showTooltip(item.pageX, item.pageY,
							item.series.label + " of " + x + " = " + y);
					}
				} else {
					$("#tooltip").remove();
					previousPoint = null;
				}
			});
		}
		
		//Select chart
        function chart_select() {
				// setup plot
				function getData(x1, x2) {

					var d = [];
					for (var i = 0; i <= 100; ++i) {
						var x = x1 + i * (x2 - x1) / 100;
						d.push([x, Math.cos(x * Math.sin(x))]);
					}

					return [
						{ label: "cos(x sin(x))", data: d }
					];
				}

				var options = {
					grid: {
						hoverable: true,
						clickable: true,
						tickColor: "#f7f7f7",
						borderWidth: 0,
						labelMargin: 10,
						margin: {
							top: 0,
							left: 5,
							bottom: 0,
							right: 0
						}
					},
					legend: {
						show: false
					},
					series: {
						lines: {
							show: true
						},
						shadowSize: 0,
						points: {
							show: true
						}
					},
					colors: ["#D9534F"],
					yaxis: {
						ticks: 10
					},
					selection: {
						mode: "xy",
						color: "#F1ADAC"
					}
				};

				var startData = getData(0, 3 * Math.PI);

				var plot = $.plot("#placeholder", startData, options);

				// Create the overview plot

				var overview = $.plot($("#overview"), startData, {
					legend: {
						show: false
					},
					series: {
						lines: {
							show: true,
							lineWidth: 1
						},
						shadowSize: 0
					},
					xaxis: {
						ticks: 4
					},
					yaxis: {
						ticks: 3,
						min: -2,
						max: 2
					},
					colors: ["#D9534F"],
					grid: {
						color: "#999",
						borderWidth: 0,
					},
					selection: {
						mode: "xy",
						color: "#F1ADAC"
					}
				});

				// now connect the two

				$("#placeholder").bind("plotselected", function (event, ranges) {

					// clamp the zooming to prevent eternal zoom

					if (ranges.xaxis.to - ranges.xaxis.from < 0.00001) {
						ranges.xaxis.to = ranges.xaxis.from + 0.00001;
					}

					if (ranges.yaxis.to - ranges.yaxis.from < 0.00001) {
						ranges.yaxis.to = ranges.yaxis.from + 0.00001;
					}

					// do the zooming

					plot = $.plot("#placeholder", getData(ranges.xaxis.from, ranges.xaxis.to),
						$.extend(true, {}, options, {
							xaxis: { min: ranges.xaxis.from, max: ranges.xaxis.to },
							yaxis: { min: ranges.yaxis.from, max: ranges.yaxis.to }
						})
					);

					// don't fire event on the overview to prevent eternal loop

					overview.setSelection(ranges, true);
				});

				$("#overview").bind("plotselected", function (event, ranges) {
					plot.setSelection(ranges);
				});

				// Add the Flot version string to the footer

				$("#footer").prepend("Flot " + $.plot.version + " &ndash; ");

        }
		
		//Revenue chart
		function chart_revenue() {
			var likes = [[1, Math.random()*100], [2, Math.random()*100], [3, Math.random()*100], [4, Math.random()*100],[5,Math.random()*100],[6, Math.random()*100],[7, Math.random()*100],[8, Math.random()*100],[9, Math.random()*100],[10, Math.random()*100],[11, Math.random()*100],[12, Math.random()*100]];
		
			var chartColor = $(this).parent().parent().css("color");
			
			var plot = $.plot($("#chart-revenue"),
				   [ { data: likes} ], {
					   series: {
						   label: "Revenue",
						   lines: { 
								show: true,
								lineWidth: 3, 
								fill: false
						   },
						   points: { 
								show: true, 
								lineWidth: 3,
								fill: true,
								fillColor: chartColor 
						   },	
						   shadowSize: 0
					   },
					   grid: { hoverable: true, 
							   clickable: true, 
							   tickColor: "rgba(255,255,255,.15)",
							   borderColor: "rgba(255,255,255,0)"
							 },
					   colors: ["#fff"],
					   xaxis: {
							font: {
								color: "#fff"
							},
							ticks:6, 
							tickDecimals: 0, 
							tickColor: chartColor,
					   },
					   yaxis: {
							font: {
								color: "#fff"
							},
							ticks:4, 
							tickDecimals: 0,
							autoscaleMargin: 0.000001
					   },
					   legend: {
							show: false
					   }
					 });

			function showTooltip(x, y, contents) {
				$('<div id="tooltip">' + contents + '</div>').css( {
					position: 'absolute',
					display: 'none',
					top: y + 5,
					left: x + 5,
					border: '1px solid #fdd',
					padding: '2px',
					'background-color': '#dfeffc',
					opacity: 0.80
				}).appendTo("body").fadeIn(200);
			}

			var previousPoint = null;
			$("#chart-revenue").bind("plothover", function (event, pos, item) {
				$("#x").text(pos.x.toFixed(2));
				$("#y").text(pos.y.toFixed(2));

					if (item) {
						if (previousPoint != item.dataIndex) {
							previousPoint = item.dataIndex;

							$("#tooltip").remove();
							var x = item.datapoint[0].toFixed(2),
								y = item.datapoint[1].toFixed(2);

							showTooltip(item.pageX, item.pageY,
										item.series.label + " on " + x + " = " + y);
						}
					}
					else {
						$("#tooltip").remove();
						previousPoint = null;
					}
			});
		}
		
		//Run the charts
		chartMonth();
		chart_select();
		chart_revenue();
		
		//Pie 1
		$('#dash_pie_1').easyPieChart({
			easing: 'easeOutBounce',
			onStep: function(from, to, percent) {
				$(this.el).find('.percent').text(Math.round(percent)+"%");
			},
			lineWidth: 6,
			barColor: Theme.colors.purple
		});
		var chart1 = window.chart = $('#dash_pie_1').data('easyPieChart');
		//Pie 2
		$('#dash_pie_2').easyPieChart({
			easing: 'easeOutBounce',
			onStep: function(from, to, percent) {
				$(this.el).find('.percent').text(Math.round(percent)+"%");
			},
			lineWidth: 6,
			barColor: Theme.colors.yellow
		});
		var chart2 = window.chart = $('#dash_pie_2').data('easyPieChart');
		//Pie 3
		$('#dash_pie_3').easyPieChart({
			easing: 'easeOutBounce',
			onStep: function(from, to, percent) {
				$(this.el).find('.percent').text(Math.round(percent)+"%");
			},
			lineWidth: 6,
			barColor: Theme.colors.pink
		});
		var chart3 = window.chart = $('#dash_pie_3').data('easyPieChart');
		
		//Update the charts
		$('.js_update').on('click', function() {
			chart1.update(Math.random()*100);
			chart2.update(Math.random()*100);
			chart3.update(Math.random()*100);
			chart_revenue();
		});
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handles vertically growing bars
	/*-----------------------------------------------------------------------------------*/
	var handleVerticalChart = function () {
		if($('.verticalChart')) {		
			$('.singleBar').each(function(){				
				var percent = $(this).find('.value span').html();				
				$(this).find('.value').animate({height:percent}, 2000, function() {					
					$(this).find('span').fadeIn();				 
				});				
			});
		}
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handles theme skin switches
	/*-----------------------------------------------------------------------------------*/
	var handleThemeSkins = function () {
		// Handle theme colors
        var setSkin = function (color) {
            $('#skin-switcher').attr("href", "components/cloudadmin/dist/css/themes/" + color + ".css");
            $.cookie('skin_color', color);
        }
		$('ul.skins > li a').click(function () {
            var color = $(this).data("skin");
            setSkin(color);
        });
		
		//Check which theme skin is set
		 if ($.cookie('skin_color')) {
            setSkin($.cookie('skin_color'));
        }
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handles Gritter on Load
	/*-----------------------------------------------------------------------------------*/
	var handleGritter = function () {
		if ($.cookie('gritter_show')) {
                return;
            }

            $.cookie('gritter_show', 1);
            setTimeout(function () {
                var unique_id = $.gritter.add({
                    // (string | mandatory) the heading of the notification
                    title: 'Welcome to Cloud Admin!',
                    // (string | mandatory) the text inside the notification
                    text: 'Cloud is a feature-rich Responsive Admin Dashboard Template with a wide array of plugins!',
                    // (string | optional) the image to display on the left
                    image: 'img/gritter/cloud.png',
                    // (bool | optional) if you want it to fade out on its own or just sit there
                    sticky: true,
                    // (int | optional) the time you want it to be alive for before fading out
                    time: '',
                    // (string | optional) the class name you want to apply to that specific message
                    class_name: 'my-sticky-class'
                });

                // You can have it return a unique id, this can be used to manually remove it later using
                setTimeout(function () {
                    $.gritter.remove(unique_id, {
                        fade: true,
                        speed: 'slow'
                    });
                }, 12000);
            }, 2000);

            setTimeout(function () {
                var unique_id = $.gritter.add({
                    // (string | mandatory) the heading of the notification
                    title: 'Customize Cloud Admin!',
                    // (string | mandatory) the text inside the notification
                    text: 'Cloud Admin is easily customizable, lightweight and has a great User Experience.',
                    // (string | optional) the image to display on the left
                    image: 'img/gritter/settings.png',
                    // (bool | optional) if you want it to fade out on its own or just sit there
                    sticky: true,
                    // (int | optional) the time you want it to be alive for before fading out
                    time: '',
                    // (string | optional) the class name you want to apply to that specific message
                    class_name: 'my-sticky-class'
                });

                // You can have it return a unique id, this can be used to manually remove it later using
                setTimeout(function () {
                    $.gritter.remove(unique_id, {
                        fade: true,
                        speed: 'slow'
                    });
                }, 13000);
            }, 8000);

            setTimeout(function () {

                $.extend($.gritter.options, {
                    position: 'top-left'
                });

                var unique_id = $.gritter.add({
                    position: 'top-left',
                    // (string | mandatory) the heading of the notification
                    title: 'Buy Cloud Admin!',
                    // (string | mandatory) the text inside the notification
                    text: 'Purchase Cloud Admin theme and get access to future updates at no extra cost. Buy now!',
                    // (string | optional) the image to display on the left
                    image: 'img/gritter/buy.png',
                    // (bool | optional) if you want it to fade out on its own or just sit there
                    sticky: true,
                    // (int | optional) the time you want it to be alive for before fading out
                    time: '',
                    // (string | optional) the class name you want to apply to that specific message
                    class_name: 'my-sticky-class'
                });

                $.extend($.gritter.options, {
                    position: 'top-right'
                });

                // You can have it return a unique id, this can be used to manually remove it later using
                setTimeout(function () {
                    $.gritter.remove(unique_id, {
                        fade: true,
                        speed: 'slow'
                    });
                }, 15000);

            }, 15000);

            setTimeout(function () {

                $.extend($.gritter.options, {
                    position: 'top-left'
                });

                var unique_id = $.gritter.add({
                    // (string | mandatory) the heading of the notification
                    title: 'Notification',
                    // (string | mandatory) the text inside the notification
                    text: 'You have 6 new notifications.',
                    // (bool | optional) if you want it to fade out on its own or just sit there
                    sticky: true,
                    // (int | optional) the time you want it to be alive for before fading out
                    time: '',
                    // (string | optional) the class name you want to apply to that specific message
                    class_name: 'my-sticky-class'
                });

                setTimeout(function () {
                    $.gritter.remove(unique_id, {
                        fade: true,
                        speed: 'slow'
                    });
                }, 4000);

                $.extend($.gritter.options, {
                    position: 'top-right'
                });

            }, 20000);

            setTimeout(function () {

                $.extend($.gritter.options, {
                    position: 'top-left'
                });

                var unique_id = $.gritter.add({
                    // (string | mandatory) the heading of the notification
                    title: 'Inbox',
                    // (string | mandatory) the text inside the notification
                    text: 'You have 5 new messages in your inbox.',
                    // (bool | optional) if you want it to fade out on its own or just sit there
                    sticky: true,
                    // (int | optional) the time you want it to be alive for before fading out
                    time: '',
                    // (string | optional) the class name you want to apply to that specific message
                    class_name: 'my-sticky-class'
                });

                $.extend($.gritter.options, {
                    position: 'top-right'
                });

                setTimeout(function () {
                    $.gritter.remove(unique_id, {
                        fade: true,
                        speed: 'slow'
                    });
                }, 4000);

            }, 25000);
	}
	/*-----------------------------------------------------------------------------------*/
	/*	Handles Profile Edit
	/*-----------------------------------------------------------------------------------*/
	var handleProfileEdit = function () {
		$(".datepicker").datepicker();
	}
	return {

        //Initialise theme pages
        init: function () {
		
            // if (App.isPage("index")) {
			// 	handleDateTimePickers(); //Function to display Date Timepicker
			// 	handleSparkline();		//Function to display Sparkline charts
			// 	handleDashFlotCharts(); //Function to display flot charts in dashboard
			// 	handleChat('chat-window'); //Function to handle chat
			// 	handleCalendar();	//Function to display calendar
			// 	handleGritter();	//Function to display Gritter notifications
            // }
			// if (App.isPage("widgets_box")) {
			// 	handleBoxSortable(); //Function to handle Box sortables
            // }
			// if (App.isPage("elements")) {
			// 	handleBootbox(); //Function to display Date Timepicker
			// 	handleMagicSuggest(); //Function to display autosuggest
			// 	handleDateColorpicker(); //Function to handle date and time picker
			// 	handleRaty(); //To show star ratings
			// 	handleTimeAgo(); //Function to handle timestamps
            // }
			// if (App.isPage("button_icons")) {
			// 	handleStatefulButtons(); //Function to display stateful buttons
			// 	handleToggle(); 		//Function to handle toggle buttons
            // }
			// if (App.isPage("sliders_progress")) {
			// 	handleSliders(); //Function to display sliders
			// 	handleProgress(); //Function to display progress bars
			// 	handleKnobs();	//Function to display knobs
            // }
			// if (App.isPage("treeview")) {
			// 	handleTree();	//Function to handle tree display
			// }
			// if (App.isPage("nestable_lists")) {
			// 	handleNestableLists();	//Function to handle nestable lists
			// }
			// if (App.isPage("simple_table")) {
			// 	handleTablecloth();	//Function to display tablecloth.js options
			// }
			// if (App.isPage("dynamic_table")) {
			// 	handleDataTables();	//Function to display data tables
			// }
			// if (App.isPage("jqgrid_plugin")) {
			// 	handleJqgrid();	//Function to display jqGrid
			// }
			// if (App.isPage("forms")) {
			// 	handleTypeahead();	//Function to display autocomplete
			// 	handleAutosize(); //Function to handle textarea autosize
			// 	handleCountable(); //Function to handle char count
			// 	handleSelect2(); //Function to handle select2
			// 	handleUniform();	//Function to handle uniform inputs
			// 	handleTimeAgo(); //Function to handle timestamps
			// }
			// if (App.isPage("rich_text_editors")) {
			// 	handleWysiwyg();	//Function to display wysiwyg
			// }
			// if (App.isPage("dropzone_file_upload")) {
			// 	handleDropzone();	//Function to display wysiwyg
			// }
			// if (App.isPage("xcharts")) {
			// 	handleXcharts();	//Function to display xcharts
			// }
			// if (App.isPage("others")) {
			// 	handleGage();	//Function to display justgage
			// 	handleEasyPie();	//Function to display easy pie charts
			// 	handleSparkline();	//Function to display sparklines
			// }
			// if (App.isPage("calendar")) {
			// 	handleCalendar();	//Function to display calendar
			// 	handleUniform();	//Function to handle uniform inputs
			// }
			// if (App.isPage("vector_maps")) {
			// 	handleJqvmaps();	//Function to display vector maps
			// }
			// if (App.isPage("gallery")) {
			// 	handleIsotope();	//Function to display portfolio
			// 	handleHover();		//Function to display hover-content
			// 	handleColorbox();		//Function to display colorbox
			// }
			// if (App.isPage("login")) {
			// 	handleUniform();	//Function to handle uniform inputs
			// }
			// if (App.isPage("wizards_validations")) {
			// 	handleUniform();	//Function to handle uniform inputs
			// }
			if (App.isPage("login_bg")) {
				handleUniform();	//Function to handle uniform inputs
				// handleBackstretch();	//Function to handle background images
			}
			// if (App.isPage("chats")) {
			// 	handleChat('chat-window');	//Function to handle chat
			// 	handleChat('chat-widget');	//Function to handle chat
			// 	initTimeAgo(); //Function to init timestamps
			// }
			// if (App.isPage("todo_timeline")) {
			// 	handleTimeline();	//Function to display timeline
			// }
			// if (App.isPage("address_book")) {
			// 	handleSliderNav();	//Function to display address book
			// }
			// if (App.isPage("orders")) {
			// 	initTimeAgo(); //Function to init timestamps
			// }
			// if (App.isPage("faq")) {
			// 	handleActiveToggle(); //Function to handle active toggle
			// }
			// if (App.isPage("user_profile")) {
			// 	handleProfileSkillPie(); //Function to show skills in pie
			// 	handleSparkline();	//Function to display sparklines
			// 	handleUniform();	//Function to handle uniform inputs
			// 	handleProfileEdit();	//Function to handle profile edit tab
			// }
			// if (App.isPage("mini_sidebar")) {
			// 	collapseSidebar();	//Function to display mini menu
			// }
			// if (App.isPage("fixed_header_sidebar")) {
			// 	handleFixedSidebar();	//Function to display fixed sidebar
			// }
			// checkLayout();	//Function to check if mini menu/fixed header is activated
			// handleSidebar(); //Function to display the sidebar
			// handleSidebarCollapse(); //Function to hide or show sidebar
			// handleSidebarAndContentHeight();  //Function to hide sidebar and main content height
			// responsiveSidebar();		//Function to handle sidebar responsively
			// handleTeamView(); //Function to toggle team view
			// handleHomePageTooltips(); //Function to handle tooltips
			// handleBoxTools(); //Function to handle box tools
			// handleSlimScrolls(); //Function to handle slim scrolls
			// handlePopovers(); //Function to handle popovers
			// handleMessenger(); //Function to handle messenger
			// handleAlerts(); //Function to handle alerts
			// handleCustomTabs(); //Function to handle min-height of custom tabs
			// handleGoToTop(); 	//Funtion to handle goto top buttons
			// handleNavbarFixedTop();		//Function to check & handle if navbar is fixed top
			// handleThemeSkins();		//Function to handle theme skins
        },

        //Set page
        setPage: function (name) {
            currentPage = name;
        },

        isPage: function (name) {
            return currentPage == name ? true : false;
        },
		//public function to add callback a function which will be called on window resize
        addResponsiveFunction: function (func) {
            responsiveFunctions.push(func);
        },
		// wrapper function to scroll(focus) to an element
        scrollTo: function (el, offeset) {
            pos = (el && el.size() > 0) ? el.offset().top : 0;
            jQuery('html,body').animate({
                scrollTop: pos + (offeset ? offeset : 0)
            }, 'slow');
        },

        // function to scroll to the top
        scrollTop: function () {
            App.scrollTo();
        },
		// initializes uniform elements
        initUniform: function (els) {
            if (els) {
                jQuery(els).each(function () {
                    if ($(this).parents(".checker").size() == 0) {
                        $(this).show();
                        $(this).uniform();
                    }
                });
            } else {
                handleAllUniform();
            }
        },
		// wrapper function to  block element(indicate loading)
        blockUI: function (el, loaderOnTop) {
            lastBlockedUI = el;
            jQuery(el).block({
                message: '<img src="./img/loaders/12.gif" align="absmiddle">',
                css: {
                    border: 'none',
                    padding: '2px',
                    backgroundColor: 'none'
                },
                overlayCSS: {
                    backgroundColor: '#000',
                    opacity: 0.05,
                    cursor: 'wait'
                }
            });
        },

        // wrapper function to  un-block element(finish loading)
        unblockUI: function (el) {
            jQuery(el).unblock({
                onUnblock: function () {
                    jQuery(el).removeAttr("style");
                }
            });
        },
    };
}();
(function (a, b) {
    a.fn.admin_tree = function (d) {
        var c = {
            "open-icon": "fa fa-folder-open",
            "close-icon": "fa fa-folder",
            selectable: true,
            "selected-icon": "fa fa-check",
            "unselected-icon": "tree-dot"
        };
        c = a.extend({}, c, d);
        this.each(function () {
            var e = a(this);
            e.html('<div class = "tree-folder" style="display:none;">				<div class="tree-folder-header">					<i class="' + c["close-icon"] + '"></i>					<div class="tree-folder-name"></div>				</div>				<div class="tree-folder-content"></div>				<div class="tree-loader" style="display:none"></div>			</div>			<div class="tree-item" style="display:none;">				' + (c["unselected-icon"] == null ? "" : '<i class="' + c["unselected-icon"] + '"></i>') + '				<div class="tree-item-name"></div>			</div>');
            e.addClass(c.selectable == true ? "tree-selectable" : "tree-unselectable");
            e.tree(c)
        });
        return this
    }
})(window.jQuery);


(function () {
    this.Theme = (function () {
        function Theme() {}
        Theme.colors = {
			white: "#FFFFFF",
			primary: "#5E87B0",
            red: "#D9534F",
            green: "#A8BC7B",
            blue: "#70AFC4",
            orange: "#F0AD4E",
			yellow: "#FCD76A",
            gray: "#6B787F",
            lightBlue: "#D4E5DE",
			purple: "#A696CE",
			pink: "#DB5E8C",
			dark_orange: "#F38630"
        };
        return Theme;
    })();
})(window.jQuery);