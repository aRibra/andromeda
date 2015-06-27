/**
 * CouponSystem Scripts
 */

function setDates() {

	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth();
	var day = now.getDate();

	$(".day").attr("min", day);
	$(".month").attr("min", month);
	$(".year").attr("min", year);

}

function getDay(dateStr) {

	var date = new Date(dateStr);
	var day = date.getDate();
	return day;

}

function getMonth(dateStr) {

	var date = new Date(dateStr);
	var month = date.getMonth();

	return month;

}

function getYear(dateStr) {

	var date = new Date(dateStr);
	var year = date.getFullYear();

	return year;

}

function loadClientInfo(){
	
	var response = getParameterByName("response");
	var json = JSON.parse(response);

	if (json.clientType == "ADMIN") {
		$("#company-div").remove();
		$("#customer-div").remove();
	} else if (json.clientType == "COMPANY") {
		$("#admin-div").remove();
		$("#customer-div").remove();
	} else if (json.clientType == "CUSTOMER") {
		$("#admin-div").remove();
		$("#company-div").remove();
	}
	
	$('#info_name').html(json.clientName);
	$('#info_id').html(json.clientId);
	$('#info_type').html(json.clientType);
	
}

function displayCustomerInfo(){
	
	
	
}


function getCompanyCouponsByType() {

	$('#get_coupons_by_type_result').html('');

	var formData = $('form[name="get_coupons_by_type_form"]').serialize();

	$
			.ajax({
				url : "rest/company_service/get_all_company_coupons",
				type : "POST",
				data : formData,

				success : function(data, textStatus, jqXHR) {
					var content = "";

					var header = "<table id='couponsTable' border ='1'> <tr> "
							+ "<th>ID</th>  <th>Coupon Title</th>  <th>Amount</th>  <th>Start Date</th>  "
							+ "<th>End Date</th>  <th>Price</th>  <th>Type</th>  <th>Message</th>  <th>Image</th> </tr> </table>";

					$('#get_coupons_by_type_result').append(header);

					var table = $("#couponsTable").find('tbody');

					$.each(data.coupon, function(index, element) {

						var column = table.append($('<tr>'));

						column.append($('<td>').text(element.id));
						column.append($('<td>').text(element.title));
						column.append($('<td>').text(element.type));
						column.append($('<td>').text(element.amount));
						column.append($('<td>').text(element.startDate));
						column.append($('<td>').text(element.endDate));
						column.append($('<td>').text(element.price));
						column.append($('<td>').text(element.message));
						column.append($('<td>').text(element.image));

					});

					// $('#get_coupons_by_type_result').append(content);

				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(textStatus + " : " + errorThrown);
				}

			});

}

function getAllCoupons() {

	$('#get_allcoupons_result').html('');

	var formData = $('form[name="get_allcoupons_form"]').serialize();

	$
			.ajax({
				url : "rest/company_service/get_all_coupons",
				type : "POST",
				data : formData,

				success : function(data, textStatus, jqXHR) {
					var json = JSON.parse(JSON.stringify(data));

					var header = "<table border ='1'> <tr> "
							+ "<th>ID</th>  <th>Coupon Title</th>  <th>Amount</th>  <th>Start Date</th>  "
							+ "<th>End Date</th>  <th>Price</th>  <th>Type</th>  <th>Message</th>  <th>Image</th> "
							+ " </tr> </table>";

					$('#get_allcoupons_result').append(header);

					var content = "";

					$
							.each(
									data.coupon,
									function(index, element) {

										var dayStart = getDay(element.startDate);
										var monthStart = getMonth(element.startDate);
										var yearStart = getYear(element.startDate);

										var dayEnd = getDay(element.endDate);
										var monthEnd = getMonth(element.endDate);
										var yearEnd = getYear(element.endDate);

										content = "";
										content = "<form name='update_coupon_form"
												+ element.id
												+ "' >"
												+ "<table border='1'>"

												+ "<tr>"
												+ "<td>"
												+ "<input type='text' name='COUPON_ID' value='"
												+ element.id
												+ "' readonly='readonly' />"
												+ "</td> "

												+ "<td>"
												+ "<input type='text' name='TITLE' class='coupon_edit_cell"
												+ element.id
												+ "' value='"
												+ element.title
												+ "' readonly='readonly' />"
												+ "</td>"

												+ "<td>"
												+ "<input type='text' name='AMOUNT' class='coupon_edit_cell"
												+ element.id
												+ "' value='"
												+ element.amount
												+ "' readonly='readonly' />"
												+ "</td>"

												+ "<td>"

												+ "<tr>"
												+ "YYYY<input type='text' name='START_DATE_YEAR' class='coupon_edit_cell"
												+ element.id
												+ "' value='"
												+ yearStart
												+ "' readonly='readonly' />"
												+ "</tr>"

												+ "<tr>"
												+ "MM<input type='text' name='START_DATE_MONTH' class='coupon_edit_cell"
												+ element.id
												+ "' value='"
												+ monthStart
												+ "' readonly='readonly' />"
												+ "</tr>"

												+ "<tr>"
												+ "DD<input type='text' name='START_DATE_DAY' class='coupon_edit_cell"
												+ element.id
												+ "' value='"
												+ dayStart
												+ "' readonly='readonly' />"
												+ "</tr>"

												+ "</td>"

												+ "<td>"

												+ "<tr>"
												+ "YYYY<input type='text' name='END_DATE_YEAR' class='coupon_edit_cell"
												+ element.id
												+ "' value='"
												+ yearEnd
												+ "' readonly='readonly' />"
												+ "</tr>"

												+ "<tr>"
												+ "MM<input type='text' name='END_DATE_MONTH' class='coupon_edit_cell"
												+ element.id
												+ "' value='"
												+ monthEnd
												+ "' readonly='readonly' />"
												+ "</tr>"

												+ "<tr>"
												+ "DD<input type='text' name='END_DATE_DAY' class='coupon_edit_cell"
												+ element.id
												+ "' value='"
												+ dayEnd
												+ "' readonly='readonly' />"
												+ "</tr>"

												+ "</td>"

												+ "<td>"
												+ "<input type='text' name='MESSAGE' class='coupon_edit_cell"
												+ element.id
												+ "' value='"
												+ element.message
												+ "' readonly='readonly' />"
												+ "</td>"

												+ "<td>"
												+ "<input type='text' name='PRICE' class='coupon_edit_cell"
												+ element.id
												+ "' value='"
												+ element.price
												+ "' readonly='readonly' />"
												+ "</td>"

												+ "<td>"
												+ "<input type='text' name='TYPE' class='coupon_edit_cell"
												+ element.id
												+ "' value='"
												+ element.type
												+ "' readonly='readonly' />"
												+ "</td>"

												+ "<td>"
												+ "<input type='text' name='IMAGE' class='coupon_edit_cell"
												+ element.id
												+ "' value='"
												+ element.image
												+ "' readonly='readonly' />"
												+ "</td>"

												+ "<input type='button' onclick='removeCoupon("
												+ element.id
												+ ")' value='Remove Coupon' />"

												+ "</td>"
												+ "<td>"
												+ "<input id='coupon_update_button"
												+ element.id
												+ "' type='button' value='Update Coupon' onclick='unlockReadOnlyForCoupon("
												+ element.id
												+ ")' />"
												+ "</td>"
												+ "</tr>"
												+ "</table>" + "</form>";

										$('#get_allcoupons_result').append(
												content);
									});

				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(textStatus);
				}

			});

}

function unlockReadOnlyForCoupon(id) {

	var editCell = ".coupon_edit_cell" + id;
	$(editCell).attr('readonly', false);
	$(editCell).css('background-color', '#999999');

	var updateButton = "#coupon_update_button" + id;
	$(updateButton).prop('value', 'Send Update Info');
	$(updateButton).attr("onclick", "updateCoupon(" + id + ")");

}

function removeCoupon(id) {

	var formData = "CUSTOMER_ID=" + id;

	$.ajax({
		url : "rest/company_service/remove_coupon",
		type : "POST",
		data : formData,

		success : function(data, textStatus, jqXHR) {
			var json = JSON.parse(JSON.stringify(data));
			alert(json.message);
			$('#get_allcoupons_result').html('');
			getAllCustomers();

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(textStatus + " : " + errorThrown);
		}

	});
}

function updateCoupon(id) {

	var formName = "form[name=update_coupon_form" + id + "]";
	formData = $(formName).serialize();

	$.ajax({
		url : "rest/company_service/update_coupon",
		type : "POST",
		data : formData,

		success : function(data, textStatus, jqXHR) {
			var json = JSON.parse(JSON.stringify(data));
			alert(json.message);
			$('#get_allcoupons_result').html('');
			getAllCoupons();

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(textStatus + " : " + errorThrown);
		}

	});
}

function getCoupon() {

	$('#get_coupon_result').html('');

	var formData = $('form[name="get_coupon_form"]').serialize();

	$.ajax({
		url : "rest/company_service/get_coupon",
		type : "POST",
		data : formData,

		success : function(data, textStatus, jqXHR) {
			var json = JSON.parse(JSON.stringify(data));

			var content = "<table border='1'>";
			content += '<tr><td>' + 'ID' + '<td/>' + '<td>' + json.id
					+ '<td/></tr>';
			content += '<tr><td>' + 'Coupon Title' + '<td/>' + '<td>'
					+ json.title + '<td/></tr>';
			content += '<tr><td>' + 'Type' + '<td/>' + '<td>' + json.type
					+ '<td/></tr>';
			content += '<tr><td>' + 'Amount' + '<td/>' + '<td>' + json.amount
					+ '<td/></tr>';

			content += '<tr><td>' + 'Start Date' + '<td/>' + '<td>'
					+ json.startDate + '<td/></tr>';
			content += '<tr><td>' + 'End Date' + '<td/>' + '<td>'
					+ json.endDate + '<td/></tr>';
			content += '<tr><td>' + 'Price' + '<td/>' + '<td>' + json.price
					+ '<td/></tr>';
			content += '<tr><td>' + 'Message' + '<td/>' + '<td>' + json.message
					+ '<td/></tr>';
			content += '<tr><td>' + 'Image' + '<td/>' + '<td>' + json.image
					+ '<td/></tr>';

			content += "</table>"

			$('#get_coupon_result').append(content);

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(textStatus);
		}

	});

}

function createCompanyRequest() {

	var formData = $('form[name="create_company_form"]').serialize();

	$.ajax({
		url : "rest/admin_service/create_company",
		type : "POST",
		data : formData,

		success : function(data, textStatus, jqXHR) {
			var json = JSON.parse(JSON.stringify(data));
			alert(json.message);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(textStatus);
		}

	});

}

function getCompanyRequest() {

	$('#get_company_result').html('');

	var formData = $('form[name="get_company_form"]').serialize();

	$.ajax({
		url : "rest/admin_service/get_company",
		type : "POST",
		data : formData,

		success : function(data, textStatus, jqXHR) {
			var json = JSON.parse(JSON.stringify(data));

			var content = "<table border='1'>";
			content += '<tr><td>' + 'ID ' + '<td/>' + '<td>' + json.clientId
					+ '<td/></tr>';
			content += '<tr><td>' + 'Company Name ' + '<td/>' + '<td>'
					+ json.companyName + '<td/></tr>';
			content += '<tr><td>' + 'Email ' + '<td/>' + '<td>' + json.email
					+ '<td/></tr>';
			content += '<tr><td>' + 'Client Type' + '<td/>' + '<td>'
					+ json.clientType + '<td/></tr>';
			content += "</table>"

			$('#get_company_result').append(content);

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(textStatus);
		}

	});

}

function getAllCompaines() {

	$('#get_allcompanies_result').html('');

	var formData = $('form[name="get_allcompanies_form"]').serialize();

	$
			.ajax({
				url : "rest/admin_service/get_all_companies",
				type : "POST",
				data : formData,

				success : function(data, textStatus, jqXHR) {
					var json = JSON.parse(JSON.stringify(data));

					var header = "<table border ='1'> <tr> <th>ID</th> <th>Company Name</th> <th>Email</th> <th>Client Type </th> <th>Remove Company</th> <th>Update Company</th> </tr> </table>";
					$('#get_allcompanies_result').append(header);

					var content = "";

					$
							.each(
									data.company,
									function(index, element) {
										content = "";
										content = "<form name='update_company_form"
												+ element.clientId
												+ "' >"
												+ "<table border='1'>"
												+ "<tr>"
												+ "<td>"
												+ "<input type='text' name='COMP_ID' value='"
												+ element.clientId
												+ "' readonly='readonly' />"
												+ "</td> <td>"
												+ "<input type='text' name='COMP_NAME' class='edit_cell"
												+ element.clientId
												+ "' value='"
												+ element.clientName
												+ "' readonly='readonly' />"
												+ "</td> <td> "
												+ "<input type='text' name='EMAIL' class='edit_cell"
												+ element.clientId
												+ "' value='"
												+ element.email
												+ "' readonly='readonly' />"
												+ " </td>"
												+ "<td>"
												+ element.clientType
												+ "</td>"

												+ "<td>"

												+ "<input type='button' onclick='removeCompany("
												+ element.clientId
												+ ")' value='Remove Company' />"

												+ "</td>"

												+ "<td>"
												+ "<input id='update_button"
												+ element.clientId
												+ "' type='button' value='Update Company' onclick='unlockReadOnly("
												+ element.clientId
												+ ")' />"
												+ "</td>"
												+ "</tr>"
												+ "</table>" + "</form>";
										$('#get_allcompanies_result').append(
												content);
									});

				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(textStatus);
				}

			});

}

function removeCompany(id) {

	formData = "COMP_ID=" + id;

	$.ajax({
		url : "rest/admin_service/remove_company",
		type : "POST",
		data : formData,

		success : function(data, textStatus, jqXHR) {
			var json = JSON.parse(JSON.stringify(data));
			alert(json.message);
			$('#get_allcompanies_result').html('');
			getAllCompaines();

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(textStatus + " : " + errorThrown);
		}

	});
}

function updateCompany(id) {

	var formName = "form[name=update_company_form" + id + "]";
	formData = $(formName).serialize();

	$.ajax({
		url : "rest/admin_service/update_company",
		type : "POST",
		data : formData,

		success : function(data, textStatus, jqXHR) {
			var json = JSON.parse(JSON.stringify(data));
			alert(json.message);
			$('#get_allcompanies_result').html('');
			getAllCompaines();

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(textStatus + " : " + errorThrown);
		}

	});
}

function unlockReadOnly(id) {

	var editCell = ".edit_cell" + id;
	$(editCell).attr('readonly', false);
	$(editCell).css('background-color', '#999999');

	var updateButton = "#update_button" + id;
	$(updateButton).prop('value', 'Send Update Info');
	$(updateButton).attr("onclick", "updateCompany(" + id + ")");

}

function getCustomer() {

	$('#get_customer_result').html('');
	var formData = $('form[name="get_customer_form"]').serialize();

	$.ajax({
		url : "rest/admin_service/get_customer",
		type : "POST",
		data : formData,
		success : function(data, textStatus, jqXHR) {
			var json = JSON.parse(JSON.stringify(data));
			var content = "<table border='1'>"

			+ "<tr>" + "<th>ID</th>" + "<th>Customer Name</th>"
					+ "<th>Client Type</th>" + "</tr>"

					+ "<tr>" + "<td>" + json.clientId + "</td>"

					+ "<td>" + json.clientName + "</td>"

					+ "<td>" + json.clientType + "</td>"

					+ "</tr>" + "</table>";

			$('#get_customer_result').append(content);

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(textStatus + " : " + errorThrown);
		}

	});

}

function createCustomer() {

	var formData = $('form[name="create_customer_form"]').serialize();

	$.ajax({
		url : "rest/admin_service/create_customer",
		type : "POST",
		data : formData,
		success : function(data, textStatus, jqXHR) {
			var json = JSON.parse(JSON.stringify(data));

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(textStatus + " : " + errorThrown);
		}

	});

}

function getAllCustomers() {

	$('#get_allcustomers_result').html('');

	var formData = $('form[name="get_allcustomers_form"]').serialize();

	$
			.ajax({
				url : "rest/admin_service/get_all_customers",
				type : "POST",
				data : formData,

				success : function(data, textStatus, jqXHR) {
					var json = JSON.parse(JSON.stringify(data));

					var header = "<table border ='1'> <tr> <th>ID</th> <th>Customer Name</th> <th>Client Type </th> <th>Remove Customer</th> <th>Update Customer</th> </tr> </table>";
					$('#get_allcustomers_result').append(header);

					var content = "";

					$
							.each(
									data.customer,
									function(index, element) {

										content = "";
										content = "<form name='update_customer_form"
												+ element.clientId
												+ "' >"
												+ "<table border='1'>"
												+ "<tr>"
												+ "<td>"
												+ "<input type='text' name='CUSTOMER_ID' value='"
												+ element.clientId
												+ "' readonly='readonly' />"
												+ "</td> <td>"
												+ "<input type='text' name='CUSTOMER_NAME' class='customer_edit_cell"
												+ element.clientId
												+ "' value='"
												+ element.clientName
												+ "' readonly='readonly' />"
												+ "</td>"
												+ "<td>"
												+ element.clientType
												+ "</td>"
												+ "<td>"

												+ "<input type='button' onclick='removeCustomer("
												+ element.clientId
												+ ")' value='Remove Customer' />"

												+ "</td>"
												+ "<td>"
												+ "<input id='customer_update_button"
												+ element.clientId
												+ "' type='button' value='Update Customer' onclick='unlockReadOnlyForCsutomer("
												+ element.clientId
												+ ")' />"
												+ "</td>"
												+ "</tr>"
												+ "</table>" + "</form>";

										$('#get_allcustomers_result').append(
												content);
									});

				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(textStatus);
				}

			});

}

function unlockReadOnlyForCsutomer(id) {

	var editCell = ".customer_edit_cell" + id;
	$(editCell).attr('readonly', false);
	$(editCell).css('background-color', '#999999');

	var updateButton = "#customer_update_button" + id;
	$(updateButton).prop('value', 'Send Update Info');
	$(updateButton).attr("onclick", "updateCustomer(" + id + ")");

}

function removeCustomer(id) {

	var formData = "CUSTOMER_ID=" + id;

	$.ajax({
		url : "rest/admin_service/remove_customer",
		type : "POST",
		data : formData,

		success : function(data, textStatus, jqXHR) {
			var json = JSON.parse(JSON.stringify(data));
			alert(json.message);
			$('#get_allcustomers_result').html('');
			getAllCustomers();

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(textStatus + " : " + errorThrown);
		}

	});
}

function updateCustomer(id) {

	var formName = "form[name=update_customer_form" + id + "]";
	formData = $(formName).serialize();

	$.ajax({
		url : "rest/admin_service/update_customer",
		type : "POST",
		data : formData,

		success : function(data, textStatus, jqXHR) {
			var json = JSON.parse(JSON.stringify(data));
			alert(json.message);
			$('#get_allcustomers_result').html('');
			getAllCustomers();

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(textStatus + " : " + errorThrown);
		}

	});
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

