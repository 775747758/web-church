
//出处不可考，通过v3的jquery.textbox.js修改

(function($) {

var replaceCJK = /[\u2E80-\u9FFF\uF92C-\uFFE5]/g,
	testCJK    = /[\u2E80-\u9FFF\uF92C-\uFFE5]/;

// jQuery doesn't support a is string judgement, so I made it by myself.
function isString(obj) {
	return typeof obj == "string" || Object.prototype.toString.call(obj) === "[object String]";
}

$.fn.maxlength = function(settings) {
	//判断浏览器是否支持maxlength TODO ie8 ie9不支持，
	//需修改为maxLength取textarea的属性，更方便使用 TODO
	
	var defaults = {
		maxLength: -1,
		onInput: null,
		cjk: false,
		wild: false,
		_pasteHandler: function(event) {
			var textarea = this;
			window.setTimeout(function() {
				inputHandler.call(textarea, event);
			}, 0);
		},
		_cutHandler: function(event) {
			var textarea = this;
			window.setTimeout(function() {
				inputHandler.call(textarea, event);
			}, 0);
		},
		_keyupHandler: function(event) {
			if (opts.maxLength < 0) {
				if ($.isFunction(opts.onInput)) {
					opts.onInput.call(this, event, {maxLength: opts.maxLength, leftLength: -1});
				}
			}
			else {
				inputHandler.call(this, event);
			}
		},
		_blurHandler: function(event) {
			if (!opts.wild) {
				fixLength(this);
			}
		}
	};
	var opts = $.extend(defaults, settings);

	// This is the prefect get caret position function.
	// You can use it cross browsers.
	function getInsertPos(textbox) {
		var iPos = 0;
		if (textbox.selectionStart || textbox.selectionStart == "0") {
			iPos = textbox.selectionStart;
		}
		else if (document.selection) {
			textbox.focus();
			var range = document.selection.createRange();
			var rangeCopy = range.duplicate();
			rangeCopy.moveToElementText(textbox);
			while (range.compareEndPoints("StartToStart", rangeCopy) > 0) {
				range.moveStart("character", -1);
				iPos++;
			}
		}
		return iPos;
	}

	// This is the prefect set caret position function.
	// You can use it cross browsers.
	function setInsertPos(textbox, iPos) {
		textbox.focus();
		if (textbox.selectionStart || textbox.selectionStart == "0") {
			textbox.selectionStart = iPos;
			textbox.selectionEnd = iPos;
		}
		else if (document.selection) {
			var range = textbox.createTextRange();
			range.moveStart("character", iPos);
			range.collapse(true);
			range.select();
		}
	}

	function isGreateMaxLength(strValue, strDelete) {
		var maxLength = opts.cjk ? opts.maxLength * 2 : opts.maxLength;
		if (maxLength > 0) {
			var valueLength = (opts.cjk ? strValue.replace(replaceCJK, "lv") : strValue).replace(/\r/g, "").length;
			var deleteLength = (strDelete ? (opts.cjk ? strDelete.replace(replaceCJK, "lv") : strDelete).replace(/\r/g, "").length : 0);
			return valueLength - deleteLength > maxLength;
		} else {
			return false;
		}
	}

	function fixLength(textbox) {
		var maxLength = opts.cjk ? opts.maxLength * 2 : opts.maxLength;
		if (maxLength > 0) {
			var strValue = textbox.value.replace(/\r/g, "");
			if (isGreateMaxLength(strValue)) {
				if (opts.cjk) {
					for (var i = 0, index = 0; i < maxLength; index++) {
						if (testCJK.test(strValue.charAt(index))) {
							i += 2;
						}
						else {
							i += 1;
						}
					}
					maxLength = index;
				}

				textbox.value = strValue.substr(0, maxLength);
			}
		}
	}

	function inputHandler(event) {
		// truck extra input text
		var strValue = this.value.replace(/\r/g, "");
		if (!opts.wild && isGreateMaxLength(strValue)) {
			// remember the scroll top position.
			var scrollTop = this.scrollTop,
				insertPos = getInsertPos(this),
				deleteLength = 0;

			if (opts.cjk) {
				var overLength = strValue.replace(replaceCJK, "lv").length - opts.maxLength * 2;
				for (var i = 0; i < overLength; deleteLength++) {
					if (testCJK.test(strValue.charAt(insertPos - deleteLength - 1))) {
						i += 2;
					}
					else {
						i += 1;
					}
				}
			}
			else {
				deleteLength = strValue.length - opts.maxLength;
			}

			var iInsertToStartLength = insertPos - deleteLength;
			this.value = strValue.substr(0, iInsertToStartLength) + strValue.substr(insertPos);
			setInsertPos(this, iInsertToStartLength);

			// set back the scroll top position.
			this.scrollTop = scrollTop;
		}

		if ($.isFunction(opts.onInput)) {
			// callback for input handler
			opts.onInput.call(this, event, {
				maxLength: opts.maxLength,
				leftLength: getLeftLength(this)
			});
		}
	};

	function getSelectedText(textbox) {
		var strText = "";
		if (textbox.selectionStart || textbox.selectionStart == "0") {
			strText = textbox.value.substring(textbox.selectionStart, textbox.selectionEnd);
		}
		else {
			strText = document.selection.createRange().text;
		}
		return strText.replace(/\r/g, "");
	};

	function getLeftLength(textbox) {
		return opts.cjk ?
			Math.round((opts.maxLength * 2 - textbox.value.replace(/\r/g, "").replace(replaceCJK, "lv").length) / 2) :
			opts.maxLength - textbox.value.replace(/\r/g, "").length;
	};

	function unbindEvents(textbox, opts) {
		$(textbox)
				.unbind("paste", opts._pasteHandler)
				.unbind("cut", opts._cutHandler)
				.unbind("keyup", opts._keyupHandler)
				.unbind("blur", opts._blurHandler);
	};

	function bindEvents(textbox, opts) {
		var $textbox = $(textbox);

		if (opts.maxLength < 0) {
			$textbox.bind("keyup", opts._keyupHandler);
		} else {
			$textbox
					.bind("paste", opts._pasteHandler)
					.bind("cut", opts._cutHandler)
					.bind("keyup", opts._keyupHandler)
					.bind("blur", opts._blurHandler);

			if (!opts.wild) {
				fixLength(textbox);
			}
		}
	};

	this.maxLength = function(maxLength) {
		if (maxLength) {
			opts.maxLength = maxLength;
			return this.filter("textarea").each(function() {
				unbindEvents(this, $(this).data("textbox-opts"));
				$(this).data("textbox-opts", opts);
				bindEvents(this, opts);
			}).end();
		}
		else {
			return opts.maxLength;
		}
	};

	this.insertPos = function(value) {
		var $textbox = this.filter("textarea");

		if (typeof value == "undefined") {
			return $textbox.length ? getInsertPos($textbox[0]) : null;
		}
		else if ($textbox.length) {
			if (isString(value) && value.toLowerCase() == "start") {
				value = 0;
			}
			else if (isString(value) && value.toLowerCase() == "end") {
				value = $textbox[0].value.replace(/\r/g, "").length;
			}

			setInsertPos($textbox[0],Math.min(Math.max(parseInt(value) || 0, 0), $textbox[0].value.replace(/\r/g, "").length));
		}

		return this;
	};

	this.input = function(callback) {
		if ($.isFunction(callback)) {
			opts.onInput = callback;
			return this.filter("textarea").each(function() {
				$(this).data("textbox-opts", opts);
			}).end();
		}

		return this;
	};

	this.fixLength = function() {
		return this.filter("textarea").each(function() {
			fixLength(this);
		}).end();
	};

	this.insertText = function(strText) {
		strText = strText.replace(/\r/g, "");
		return this.filter("textarea").each(function() {
			if (opts.wild || !isGreateMaxLength(this.value + strText, getSelectedText(this))) {
				if (this.selectionStart || this.selectionStart == "0") {
					var startPos = this.selectionStart;
					var endPos = this.selectionEnd;
					var scrollTop = this.scrollTop;

					this.value = this.value.substring(0, startPos) + 
								strText + this.value.substring(endPos, this.value.length);
					this.focus();

					var cPos = startPos + strText.length;
					this.selectionStart = cPos;
					this.selectionEnd = cPos;
					this.scrollTop = scrollTop;
				}
				else if (document.selection) {
					this.focus();
					var range = document.selection.createRange();
					range.text = strText;
					range.collapse(true);
					range.select();
				}

				// fired when insert text has finished
				if ($.isFunction(opts.onInput)) {
					opts.onInput.call(this, {type: "insert"}, {
						maxLength: opts.maxLength,
						leftLength: getLeftLength(this)
					});
				}
			}
		}).end();
	};

	return this.filter("textarea").each(function() {
		var $textbox = $(this);
		if (settings) {
			if ($textbox.data("textbox-opts")) {
				unbindEvents(this, $textbox.data("textbox-opts"));
				$textbox.data("textbox-opts", opts);
				bindEvents(this, opts);
			} else {
				$textbox.data("textbox-opts", opts);
				bindEvents(this, opts);
			}
		} else {
			if ($textbox.data("textbox-opts")) {
				opts = $textbox.data("textbox-opts");
			}
		}
	}).end();
};
})(jQuery);