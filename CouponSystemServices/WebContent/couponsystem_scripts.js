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
